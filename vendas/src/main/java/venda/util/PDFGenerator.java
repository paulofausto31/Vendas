package venda.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import persistencia.brl.EmpresaBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.brl.VendedorBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.EmpresaDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.ProdutoDTO;
import persistencia.dto.VendedorDTO;
import vendas.telas.R;

public class PDFGenerator {

    public void createPDF(Context ctx, String fileName, List<ItenPedidoDTO> itens, ClienteDTO cliDTO) {
        String path = ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(path, fileName);
        ProdutoBRL proBRL = new ProdutoBRL(ctx);
        EmpresaBRL empBRL = new EmpresaBRL(ctx);
        VendedorBRL venBRL = new VendedorBRL(ctx);
        ProdutoDTO proDTO;

        try {
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Adicionando a imagem da logomarca
            EmpresaDTO empDTO = empBRL.getByCodEmpresa(Global.codEmpresa);
            Bitmap bitmap = null;
            if (empDTO.getCnpj().equals("07765220000155"))
                bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_sanazon);
            else if (empDTO.getCnpj().equals("07547684000195"))
                bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_idm);
            else if (empDTO.getCnpj().equals("01914386000100"))
                bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_al);
            else
                bitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.logo_branco);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            ImageData imageData = ImageDataFactory.create(stream.toByteArray());
            Image image = new Image(imageData);
            image.setWidth(100);
            image.setHeight(50);

            // Criando tabela para alinhar logotipo e informações da empresa

            Table headerTable = new Table(new float[]{1, 3});
            headerTable.setWidth(UnitValue.createPercentValue(100));
            headerTable.addCell(new Cell().add(image).setBorder(Border.NO_BORDER));
            headerTable.addCell(new Cell().add(new Paragraph(empDTO.getRazaoSocial().trim()).setBold())
                    .add(new Paragraph("CNPJ: ".concat(empDTO.getCnpj().trim()) ))
                    .add(new Paragraph(empDTO.getEndereco().trim()))
                    .add(new Paragraph(empDTO.getBairro().trim().concat(" - ").concat(empDTO.getCidade().trim()).concat(" - ").concat(empDTO.getUf().trim()).concat(" - ").concat(empDTO.getCep().trim())))
                    .add(new Paragraph("Fone: ".concat(empDTO.getTelefone().trim()).concat("  Email: ").concat(empDTO.getEmail().trim())))
                    .setBorder(Border.NO_BORDER));
            document.add(headerTable);

            // Adicionando um espaço de duas linhas
            document.add(new Paragraph("\n"));

            // Criando tabela para os dados do cliente e do vendedor
            Table clientTable = new Table(new float[]{2, 2});
            clientTable.setWidth(UnitValue.createPercentValue(100));

            // Adicionando título "DADOS DO CLIENTE" e "VENDEDOR" alinhado à direita
            VendedorDTO venDTO = venBRL.getVendedorEmpresa(empDTO.getCodEmpresa());
            clientTable.addCell(new Cell().add(new Paragraph("DADOS DO CLIENTE").setBold().setFontSize(10)).setBorder(Border.NO_BORDER));
            clientTable.addCell(new Cell().add(new Paragraph("VENDEDOR: ".concat(venDTO.getNome()).concat(" DATA: ").concat(dataHora.dataDia()))
                    .setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(10)).setBorder(Border.NO_BORDER));

            // Adicionando a linha sólida antes da "RAZÃO SOCIAL"
            document.add(clientTable);
            document.add(new LineSeparator(new SolidLine())); // Linha sólida adicionada aqui

            // Criando a estrutura dos dados do cliente
            Table clientDetailsTable = new Table(new float[]{2, 2});
            clientDetailsTable.setWidth(UnitValue.createPercentValue(100));

            // Adicionando as informações do cliente com fonte menor
            clientDetailsTable.addCell(new Cell().add(new Paragraph("RAZÃO SOCIAL: ").setBold().add(cliDTO.getRazaoSocial()).setFontSize(9)).setBorder(Border.NO_BORDER));
            clientDetailsTable.addCell(new Cell().add(new Paragraph("NOME FANTASIA: ").setBold().add(cliDTO.getNome()).setFontSize(9)).setBorder(Border.NO_BORDER));

            clientDetailsTable.addCell(new Cell().add(new Paragraph("CNPJ: ").setBold().add(cliDTO.getCpfCnpj()).setFontSize(9)).setBorder(Border.NO_BORDER));
            //clientDetailsTable.addCell(new Cell().add(new Paragraph("INSC. ESTADUAL: ").setBold().add("03.061138-5").setFontSize(9)).setBorder(Border.NO_BORDER));

            clientDetailsTable.addCell(new Cell().add(new Paragraph("TELEFONE: ").setBold().add(cliDTO.getTelefone()).setFontSize(9)).setBorder(Border.NO_BORDER));
            //clientDetailsTable.addCell(new Cell().add(new Paragraph("EMAIL: ").setBold().add(cliDTO.em).setFontSize(9)).setBorder(Border.NO_BORDER));

            clientDetailsTable.addCell(new Cell().add(new Paragraph("ENDEREÇO: ").setBold().add(cliDTO.getEndereco()).setFontSize(9)).setBorder(Border.NO_BORDER));
            //clientDetailsTable.addCell(new Cell().add(new Paragraph("NÚMERO: ").setBold().add(cliDTO.get).setFontSize(9)).setBorder(Border.NO_BORDER));

            clientDetailsTable.addCell(new Cell().add(new Paragraph("BAIRRO: ").setBold().add(cliDTO.getBairro()).setFontSize(9)).setBorder(Border.NO_BORDER));
            //clientDetailsTable.addCell(new Cell().add(new Paragraph("COMPLEMENTO: ").setBold().add("LETRA B").setFontSize(9)).setBorder(Border.NO_BORDER));

            //clientDetailsTable.addCell(new Cell().add(new Paragraph("CEP: ").setBold().add(cliDTO.ce).setFontSize(9)).setBorder(Border.NO_BORDER));
            //clientDetailsTable.addCell(new Cell().add(new Paragraph("UF: ").setBold().add(cliDTO.ge).setFontSize(9)).setBorder(Border.NO_BORDER));

            clientDetailsTable.addCell(new Cell().add(new Paragraph("CIDADE: ").setBold().add(cliDTO.getCidade()).setFontSize(9)).setBorder(Border.NO_BORDER));
            clientDetailsTable.addCell(new Cell().add(new Paragraph("").setFontSize(9)).setBorder(Border.NO_BORDER)); // Célula vazia para alinhamento

            document.add(clientDetailsTable);

            // Linha tracejada corrigida (DashedLine funciona melhor)
            //document.add(new LineSeparator(new DashedLine()));

            // Adicionando um espaço de duas linhas
            document.add(new Paragraph("\n"));

// Adicionando título antes da tabela
            document.add(new Paragraph("DADOS DO ORÇAMENTO").setBold().setFontSize(10));

// Linha sólida abaixo do título
            document.add(new LineSeparator(new SolidLine()));

// Criando uma tabela com as colunas corretas (sem NCM)
            float[] columnWidths = {30f, 300f, 60f, 50f, 100f, 100f}; // Ajuste das larguras
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));

// Configurando fonte menor para o cabeçalho
            float headerFontSize = 8f;
            float itemFontSize = 8f;

// Adicionando cabeçalhos formatados com fonte reduzida
            table.addHeaderCell(new Cell().add(new Paragraph("#").setBold().setFontSize(headerFontSize)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("DESCRIÇÃO").setBold().setFontSize(headerFontSize)).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("QTDE.").setBold().setFontSize(headerFontSize)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("UN.").setBold().setFontSize(headerFontSize)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("V. UNIT.").setBold().setFontSize(headerFontSize)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
            table.addHeaderCell(new Cell().add(new Paragraph("V. TOTAL").setBold().setFontSize(headerFontSize)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            DecimalFormat df = new DecimalFormat("#,##0.00");
            Double totalGeral = 0.00;
            int index = 1; // Contador para numeração

// Adicionando produtos na tabela
            for (ItenPedidoDTO item : itens) {
                proDTO = proBRL.getByCodProduto(item.getCodProduto());
                Double total = item.getQuantidade() * item.getPreco();
                totalGeral += total;

                // Número do item
                table.addCell(new Cell().add(new Paragraph(index + ".").setFontSize(itemFontSize)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                // Descrição do produto
                table.addCell(new Cell().add(new Paragraph(proDTO.getDescricao()).setFontSize(itemFontSize)).setBorder(Border.NO_BORDER));

                // Quantidade
                table.addCell(new Cell().add(new Paragraph(item.getQuantidade().toString()).setFontSize(itemFontSize)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                // Unidade (substitua por `proDTO.getUnidade()` se necessário)
                table.addCell(new Cell().add(new Paragraph(item.getUnidade().toString()).setFontSize(itemFontSize)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

                // Valor unitário
                table.addCell(new Cell().add(new Paragraph("R$ " + df.format(item.getPreco())).setFontSize(itemFontSize)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

                // Valor total
                table.addCell(new Cell().add(new Paragraph("R$ " + df.format(total)).setFontSize(itemFontSize)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

                index++;

                // Adicionando linha tracejada após cada item
                table.addCell(new Cell(1, 6).add(new LineSeparator(new DottedLine())).setBorder(Border.NO_BORDER));
            }

// Adicionando linha final com o total geral
            table.addCell(new Cell(1, 4).add(new Paragraph("TOTAL DO PEDIDO").setBold().setFontSize(headerFontSize)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)); // Célula vazia para alinhamento
            table.addCell(new Cell().add(new Paragraph("R$ " + df.format(totalGeral))).setBold().setFontSize(headerFontSize).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            document.add(table);
            document.close();
            System.out.println("PDF Created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
