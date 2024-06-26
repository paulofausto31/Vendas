package venda.util;

import android.content.Context;
import android.os.Environment;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.ProdutoDTO;

public class PDFGenerator {

    public void createPDF(Context ctx, String fileName, List<ItenPedidoDTO> itens) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(path, fileName);
        ProdutoBRL proBRL = new ProdutoBRL(ctx);
        ProdutoDTO proDTO;

        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Adicionando um título
            document.add(new Paragraph("Lista de Pedidos"));

            // Criando uma tabela com 4 colunas
            float[] columnWidths = {200f, 100f, 100f, 100f};
            Table table = new Table(columnWidths);

            // Adicionando cabeçalhos
            table.addCell(new Cell().add(new Paragraph("Nome do Produto")));
            table.addCell(new Cell().add(new Paragraph("Preço Unitário")));
            table.addCell(new Cell().add(new Paragraph("Quantidade")));
            table.addCell(new Cell().add(new Paragraph("Total")));

            // Adicionando produtos na tabela
            for (ItenPedidoDTO item : itens) {
                proDTO = proBRL.getByCodProduto(item.getCodProduto());
                Double total = item.getQuantidade() * item.getPreco();
                table.addCell(new Cell().add(new Paragraph(proDTO.getDescricao())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getPreco().toString()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantidade().toString()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(total.toString()))));
            }

            document.add(table);
            document.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
