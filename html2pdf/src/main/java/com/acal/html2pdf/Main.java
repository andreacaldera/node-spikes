package com.acal.html2pdf;

import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.IOException;

public class Main {

    private static final String OUTPUT_FOLDER = "build/pdf/";
    private static final String OUTPUT_FILE = OUTPUT_FOLDER + "output.pdf";

    private static final String LONG_TEXT_FIELD =
            "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field" +
                    "This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field This is a very long text field";


    public static void main(String[] args) throws IOException, DocumentException {
        File outputFolder = new File(OUTPUT_FOLDER);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        new Pdf()
                .withFirstField("First field value")
                .withSecondField("Second field value")
                .withEleventhFieldA(false)
                .withEleventhFieldB(true).withEleventhFieldC(false)
                .withLongText(LONG_TEXT_FIELD).create(OUTPUT_FILE);
    }

}