package venda.util;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import android.app.Activity;
import android.os.Bundle;

public class PdfActivity extends Activity {
	private static String file = "texto";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pdf);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addTitlePage(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addTitlePage(Document document) 
        throws DocumentException{
            Paragraph preface = new Paragraph();

            //pula uma linha
            addEmptyFile(preface, 1);
            //titulo com font grande
            preface.add(new Paragraph("Documento de Teste", catFont));

            addEmptyFile(preface, 1);
            preface.add(new Paragraph("Conteudo teste do corpo, um texto simples para ser exibido como corpo do documento", smallBold));
            document.add(preface);
            document.newPage();

    }
    //metodo para pular uma linha
    private static void addEmptyFile(Paragraph paragraph, int number) {
        for(int i=0; i<number; i++){
            paragraph.add(new Paragraph(""));
        }
    }           
}
