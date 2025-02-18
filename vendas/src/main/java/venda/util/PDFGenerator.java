package venda.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ProdutoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.ProdutoDTO;
import vendas.telas.R;

public class PDFGenerator {

    public void createPDF(Context ctx, String fileName, List<ItenPedidoDTO> itens, ClienteDTO cliDTO) {
        //String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        String path = ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(path, fileName);
        ProdutoBRL proBRL = new ProdutoBRL(ctx);
        ProdutoDTO proDTO;

        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Adicionando a imagem da logomarca
            Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_idm); // Substitua pelo nome do seu recurso
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            ImageData imageData = ImageDataFactory.create(stream.toByteArray());
            Image image = new Image(imageData);

            // Ajustando o tamanho da imagem
            image.setWidth(100);
            image.setHeight(50);
            document.add(image);

            // Adicionando informações do cliente
            document.add(new Paragraph("CPF/CNPJ:  " + cliDTO.getCpfCnpj()));
            document.add(new Paragraph("Cliente:  " + cliDTO.getNome()));

            // Linha tracejada
            LineSeparator ls = new LineSeparator(new DottedLine());
            document.add(new Paragraph().add(ls));

            // Criando uma tabela com 4 colunas
            float[] columnWidths = {200f, 100f, 100f, 100f};
            Table table = new Table(columnWidths);

            // Adicionando cabeçalhos
            table.addCell("Nome do Produto");
            table.addCell("Preço Unitário");
            table.addCell("Quantidade");
            table.addCell("Total");

            Double totalGeral = 0.00;
            DecimalFormat df = new DecimalFormat("#.00");

            // Adicionando produtos na tabela
            for (ItenPedidoDTO item : itens) {
                proDTO = proBRL.getByCodProduto(item.getCodProduto());
                Double total = item.getQuantidade() * item.getPreco();
                totalGeral += total;

                table.addCell(proDTO.getDescricao());
                table.addCell(df.format(item.getPreco()));
                table.addCell(item.getQuantidade().toString());
                table.addCell(df.format(total));
            }

            // Linha de total do pedido
            table.addCell(new Paragraph("Total do Pedido").setBold());
            table.addCell("");
            table.addCell("");
            table.addCell(df.format(totalGeral));

            document.add(table);
            document.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
