package com.acal.html2pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pdf {

    private static final String HEADER = "${header}";
    private static final String FIRST_FIELD = "${firstField}";
    private static final String SECOND_FIELD = "${secondField}";
    private static final String THIRD_FIELD = "${thirdField}";
    private static final String LONG_TEXT = "${longText}";
    private static final String ELEVENTH_FIELD_A = "${eleventhFieldA}";
    private static final String ELEVENTH_FIELD_B = "${eleventhFieldB}";
    private static final String ELEVENTH_FIELD_C = "${eleventhFieldC}";
    private static final String DATE_TIME = "${dateTime}";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd G ' - ' HH:mm:ss");

    private static final String CHECKBOX_CHECKED_IMAGE = "<img  width='15' height='15' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAoUlEQVR4Xu2XQQrAIAwEY/Ch9WX2pxa87EUaZGgFcUHwlBlcD5rMrNnCuC1ONiX9zG46gY0rOAJHoPb1kvwx/DKlgAoovO8rEADwQMJJhzE8Tp7pEMLv0QwHHQK44rMdEji8hJJg8PgOFMFGERDCJQAkADyuoPRB0xEcCkgCwKmAJACcCkgCwKmAJACcCkgCwfl7QEAABwIC7/EmPALnd/wAo5AsUGAzzgwAAAAASUVORK5CYII='/>";
    private static final String CHECKBOX_UNCHECKED_IMAGE = "<img width='15' height='15' src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAOklEQVR4Xu3XsREAIAgEwcex/5YxtQIx2IsId8i+knQGWxluX3flbf3FBwAAAAAAAAAAAAAAAACs4wPenwM/A5GYfQAAAABJRU5ErkJggg=='/>";

    private final String html;

    private Pdf(String html) {
        this.html = html;
    }

    public Pdf() {
        try {
            html = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/" + "pdf-template.html").getFile())))
                    .replace(DATE_TIME, DATE_FORMAT.format(new Date()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to create intel report", e);
        }
    }

    private Pdf withCheckBox(String placeholder, boolean checked) {
        return new Pdf(html.replace(placeholder, checked ? CHECKBOX_CHECKED_IMAGE : CHECKBOX_UNCHECKED_IMAGE));
    }

    public Pdf withFirstField(String firstField) {
        return new Pdf(html.replace(FIRST_FIELD, firstField));
    }

    public Pdf withSecondField(String secondField) {
        return new Pdf(html.replace(SECOND_FIELD, secondField));
    }

    public Pdf withEleventhFieldA(boolean checked) {
        return withCheckBox(ELEVENTH_FIELD_A, checked);
    }

    public Pdf withEleventhFieldB(boolean checked) {
        return withCheckBox(ELEVENTH_FIELD_B, checked);
    }

    public Pdf withEleventhFieldC(boolean checked) {
        return withCheckBox(ELEVENTH_FIELD_C, checked);
    }

    public Pdf withLongText(String longText) {
        return new Pdf(html.replace(LONG_TEXT, longText));
    }

    public void create(String filename) throws DocumentException, IOException {
        create(filename, false);
    }

    public void create(String filename, boolean flatten) throws DocumentException, IOException {
        String header = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/" + "pdf-template-header.html").getFile())));
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(filename)));
        // writer.setEncryption(null, null, PdfWriter.STANDARD_ENCRYPTION_128);
        document.open();

        CSSResolver cssResolver = new StyleAttrCSSResolver();
        cssResolver.addCss(XMLWorkerHelper.getCSS(getClass().getClassLoader().getResourceAsStream("pdf-template.css")));

        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeline);
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(html.replace(HEADER, header).getBytes()));
        document.close();
        if (flatten) {
            flatten(filename);
        }
    }

//    public void old(String filename) throws DocumentException, IOException {
//        String header = new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/" + "pdf-template-header.html").getFile())));
//        // TODO better output stream management
//        OutputStream file = new FileOutputStream(new File(filename));
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, file);
//        document.open();
//        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(html.replace(HEADER, header).getBytes()));
//        document.close();
//        file.close();
//    }

    public void flatten(String filename) throws IOException, DocumentException {
        PdfReader reader;
        PdfStamper stamper;
        ByteArrayOutputStream baos;
        reader = new PdfReader(filename);
        baos = new ByteArrayOutputStream();
        stamper = new PdfStamper(reader, baos);
        AcroFields fields = stamper.getAcroFields();
        fields.setGenerateAppearances(true);
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();
        reader = new PdfReader(baos.toByteArray());
        reader.close();
    }

}