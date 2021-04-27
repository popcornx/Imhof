package ch.epfl.imhof.osm;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public final class OSMMapReader {
    /**
     *
     */
    private static final String RELATION = "relation";
    private static OSMMap.Builder mb;
    private static final Projection proj = new CH1903Projection();

    private OSMMapReader() {
    }

    public static OSMMap readOSMFile(String fileName, boolean unGZip) throws SAXException, IOException {
        mb = new OSMMap.Builder();
        if (unGZip) {
            try (InputStream i = new GZIPInputStream(new FileInputStream(fileName))) {
                parse(i);
                return mb.build();
            }
        } else {
            try (InputStream i = new FileInputStream(fileName)) {
                parse(i);
                return mb.build();
            }
        }
    }

    private static void parse(InputStream i) throws SAXException, IOException {
        XMLReader r = XMLReaderFactory.createXMLReader();
        r.setContentHandler(new DefaultHandler() {

            private OSMNode.Builder nb;
            private OSMWay.Builder wb;
            private OSMRelation.Builder rb;
            private OSMRelation.Member.Type currentElementType;

            @Override
            public void startElement(String uri, String lName, String qName, Attributes atts) {
                OSMNode n;
                switch (qName) {
                    case "node":
                        currentElementType = OSMRelation.Member.Type.NODE;
                        Point p = new Point(Double.parseDouble(atts.getValue("lat")),
                                Double.parseDouble(atts.getValue("lon")));
                        nb = new OSMNode.Builder(Long.parseLong(atts.getValue("id")), proj.inverse(p));
                        break;
                    case "way":
                        currentElementType = OSMRelation.Member.Type.WAY;
                        wb = new OSMWay.Builder(Long.parseLong(atts.getValue("id")));
                        break;
                    case "nd":
                        if ((n = mb.nodeForId(Long.parseLong(atts.getValue("ref")))) != null) {
                            wb.addNode(n);
                        } else {
                            wb.setIncomplete();
                        }
                        break;
                    case "tag":
                        switch (currentElementType) {
                            case WAY:
                                wb.setAttribute(atts.getValue("k"), atts.getValue("v"));
                                break;
                            case RELATION:
                                rb.setAttribute(atts.getValue("k"), atts.getValue("v"));
                                break;
                            default:
                                break;
                        }
                        break;
                    case RELATION:
                        currentElementType = OSMRelation.Member.Type.RELATION;
                        rb = new OSMRelation.Builder(Long.parseLong(atts.getValue("id")));
                        break;
                    case "member":
                        switch (atts.getValue("type")) {
                            case "node":
                                if ((n = mb.nodeForId(Long.parseLong(atts.getValue("ref")))) != null) {
                                    rb.addMember(OSMRelation.Member.Type.NODE, atts.getValue("role"), n);
                                } else {
                                    rb.setIncomplete();
                                }
                                break;
                            case "way":
                                OSMWay w;
                                if ((w = mb.wayForId(Long.parseLong(atts.getValue("ref")))) != null) {
                                    rb.addMember(OSMRelation.Member.Type.WAY, atts.getValue("role"), w);
                                } else {
                                    rb.setIncomplete();
                                }
                                break;
                            case RELATION:
                                OSMRelation r;
                                if ((r = mb.relationForId(Long.parseLong(atts.getValue("ref")))) != null) {
                                    rb.addMember(OSMRelation.Member.Type.RELATION, atts.getValue("role"), r);
                                } else {
                                    rb.setIncomplete();
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void endElement(String uri, String lName, String qName) {
                switch (qName) {
                    case "node":
                        try {
                            mb.addNode(nb.build());
                        } catch (IllegalStateException e) {
                        }
                        break;
                    case "way":
                        try {
                            mb.addWay(wb.build());
                        } catch (IllegalStateException e) {
                        }
                        break;
                    case RELATION:
                        try {
                            mb.addRelation(rb.build());
                        } catch (IllegalStateException e) {
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        r.parse(new InputSource(i));
    }
}