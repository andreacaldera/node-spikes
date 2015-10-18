package com.acal.html2pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;

import java.io.IOException;

public class Base64ImageProvider extends AbstractImageProvider {

    @Override
    public Image retrieve(String src) {
        int pos = src.indexOf("base64,");
        try {
            if (src.startsWith("data") && pos > 0) {
                byte[] img = com.itextpdf.text.pdf.codec.Base64.decode(src.substring(pos + 7));
                return Image.getInstance(img);
            } else {
                return Image.getInstance(src);
            }
        } catch (BadElementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getImageRootPath() {
        return null;
    }

}