package examinador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import examinador.constructor;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class hojaRespuestasPDF {

    private String nombreArchivo;
    private Paragraph nu;
    private static final Font Tit = FontFactory.getFont(FontFactory.COURIER, 22, Font.BOLDITALIC);
    private static final Font raya = FontFactory.getFont(FontFactory.COURIER_BOLD);
    ArrayList<constructor> datExa = new ArrayList<>();
    int colTabla;

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void AbrirArchivo() throws IOException  {
        try {
            FileInputStream fis = new FileInputStream("dat/" + nombreArchivo + ".dat");
            ObjectInputStream entrada = new ObjectInputStream(fis);
            datExa = (ArrayList<constructor>) entrada.readObject();
            entrada.close();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public void Hoja(File pdfNewFile) throws IOException {
        constructor con = new constructor();
        try {
            Document documento = new Document(PageSize.LETTER);
            try {
                PdfWriter.getInstance(documento, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            documento.open();
            documento.addAuthor("Examinator-X");
            AbrirArchivo();
            int nPre = datExa.size() - 1;
            if (datExa.size() < 14) {
                colTabla = 1;
            } else if (datExa.size() < 34) {
                colTabla = 2;
            } else if (datExa.size() < 54) {
                colTabla = 3;
            }

            Image logoBatiz = null, logoIpn = null, rec = null;
            try {
                rec = Image.getInstance("img/rectangulo2.png");
                logoBatiz = Image.getInstance("img/logoBatiz.png");
                logoIpn = Image.getInstance("img/logoIpn.png");
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" + ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " + ex);
            }
            com.itextpdf.text.Rectangle tamHoja = PageSize.LETTER;

            int altura, ancho;
            altura = (int) tamHoja.getHeight();
            ancho = (int) tamHoja.getWidth();
            rec.setAbsolutePosition(0, 11);
            rec.scaleAbsolute(ancho, altura);
            logoBatiz.setAbsolutePosition(50, altura - 100);
            logoBatiz.scaleAbsolute(50, 50);
            logoIpn.setAbsolutePosition(ancho - 95, altura - 100);
            logoIpn.scaleAbsolute(35, 50);
            documento.addTitle(nombreArchivo);
            documento.add(rec);
            documento.add(logoBatiz);
            documento.add(logoIpn);
            
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Element.ALIGN_CENTER);
            int donde = 0;
            for (int i = 0; i < datExa.size(); i++) {
                if (datExa.get(i).getMateria() != null) {
                    donde = i;
                }
            }
            Chunk titulo = new Chunk(nombreArchivo + System.lineSeparator());
            String escuela = "INSTITUTO POLITÉCNICO NACIONAL" + System.lineSeparator()
                    + "CECyT 9 \"Juan de Dios Batiz\"" + System.lineSeparator()
                    + System.lineSeparator();
            String fecha = "Fecha: " + datExa.get(donde).getFecha() + System.lineSeparator();
            String periodo = "Periodo: " + datExa.get(donde).getPeriodo() + ""
                    + "\t" + "                             Materia: "
                    + datExa.get(donde).getMateria() + System.lineSeparator();
            String mano1 = "Nombre:______________________________________________"
                    + "                " + "Grupo:___________" + System.lineSeparator();
            String mano2 = "Boleta:_________________" + "                           "
                    + "Profesor: "
                    + "____________" + System.lineSeparator()
                    + System.lineSeparator();
            titulo.setFont(Tit);

            parrafo.add(escuela);
            parrafo.add(titulo);
            parrafo.add(fecha);
            parrafo.add(periodo);
            float ta = parrafo.getFont().getSize();
            parrafo.getFont().setSize(17);
            parrafo.add(mano1);
            parrafo.getFont().setSize(ta);
            parrafo.add(mano2);
            parrafo.setFont(Tit);
            documento.add(parrafo);

            Image image2 = null;
            try {
                image2 = Image.getInstance("img/boleta2.png");
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" + ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " + ex);
            }
            String ins = "Rellena los ovalos de forma que indique tu numero de boleta"
                    + " si se leyera de arriba hacia abajo, en caso de un PM rellena "
                    + "los primeros 2 numeros con cero (0)";
            Paragraph instrucciones = new Paragraph(ins);
            instrucciones.getFont().setSize(6);
            PdfPTable tabla = new PdfPTable(12 * colTabla);

            tabla.setWidthPercentage((float) 33 * colTabla);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            tabla.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.getDefaultCell().setBorderColor(BaseColor.WHITE);
            if (colTabla == 1) {
                tabla.getDefaultCell().setColspan(12);
                tabla.addCell(instrucciones);
                tabla.getDefaultCell().setRowspan(7);
                tabla.addCell(image2);
                tabla.getDefaultCell().setRowspan(1);

                for (int i = 0; i < datExa.size() - 1; i++) {
                    Image image = null;
                    int h = i + 1;
                    try {
                        if (h % 2 == 0) {
                            image = Image.getInstance("img/reactivosg.png");
                        } else {
                            image = Image.getInstance("img/reactivosb.png");
                        }
                    } catch (BadElementException ex) {
                        System.out.println("Image BadElementException" + ex);
                    } catch (IOException ex) {
                        System.out.println("Image IOException " + ex);
                    }
                    tabla.getDefaultCell().setColspan(2);

                    nu = new Paragraph(h + "");
                    nu.getFont().setSize(15);
                    tabla.addCell(nu);
                    tabla.getDefaultCell().setColspan(10);
                    tabla.addCell(image);

                }
            } else if (colTabla == 2) {
                String[] vac = new String[2];
                vac[0] = " ";
                vac[1] = " ";
                int celVa = 0, celVa2 = 0;

                if (nPre > 12 && nPre < 21) {
                    celVa2 = 32 - nPre;

                    for (int i = 13; i < datExa.size() - 1; i++) {
                        Image image = null;
                        int h = i + 1;
                        try {
                            if (h % 2 == 0) {
                                image = Image.getInstance("img/reactivosg.png");
                            } else {
                                image = Image.getInstance("img/reactivosb.png");
                            }
                        } catch (BadElementException ex) {
                            System.out.println("Image BadElementException" + ex);
                        } catch (IOException ex) {
                            System.out.println("Image IOException " + ex);
                        }

                        if (i == 13) {
                            image = Image.getInstance("img/reactivosb.png");
                            tabla.getDefaultCell().setColspan(12);
                            tabla.addCell(instrucciones);
                            tabla.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(12);
                            tabla.getDefaultCell().setRowspan(7);
                            tabla.addCell(image2);
                            tabla.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }

                        tabla.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla.addCell(nu);
                        tabla.getDefaultCell().setColspan(10);
                        tabla.addCell(image);
                        if (i == datExa.size() - 2) {
                            tabla.getDefaultCell().setColspan(12);
                            tabla.getDefaultCell().setRowspan(celVa2);
                            tabla.addCell(vac[0]);
                            tabla.getDefaultCell().setRowspan(1);
                            tabla.getDefaultCell().setColspan(2);
                        }
                    }
                    for (int i = 0; i < 12; i++) {
                        Image image = null;
                        int h = i + 1;
                        try {
                            if (h % 2 == 0) {
                                image = Image.getInstance("img/reactivosg.png");
                            } else {
                                image = Image.getInstance("img/reactivosb.png");
                            }
                        } catch (BadElementException ex) {
                            System.out.println("Image BadElementException" + ex);
                        } catch (IOException ex) {
                            System.out.println("Image IOException " + ex);
                        }
                        tabla.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla.addCell(nu);
                        tabla.getDefaultCell().setColspan(10);
                        tabla.addCell(image);

                    }
                } else {
                    celVa2 = 32 - nPre;

                    for (int i = 13; i < 20; i++) {
                        Image image = null;
                        int h = i + 1;
                        try {
                            if (h % 2 == 0) {
                                image = Image.getInstance("img/reactivosg.png");
                            } else {
                                image = Image.getInstance("img/reactivosb.png");
                            }
                        } catch (BadElementException ex) {
                            System.out.println("Image BadElementException" + ex);
                        } catch (IOException ex) {
                            System.out.println("Image IOException " + ex);
                        }

                        if (i == 13) {
                            image = Image.getInstance("img/reactivosb.png");
                            tabla.getDefaultCell().setColspan(12);
                            tabla.addCell(instrucciones);
                            tabla.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(12);
                            tabla.getDefaultCell().setRowspan(7);
                            tabla.addCell(image2);
                            tabla.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }

                        tabla.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla.addCell(nu);
                        tabla.getDefaultCell().setColspan(10);
                        tabla.addCell(image);
                        if (i == 19) {
                            for (int j = 0; j < 12; j++) {
                                image = null;
                                int n = j + 1;
                                try {
                                    if (n % 2 == 0) {
                                        image = Image.getInstance("img/reactivosg.png");
                                    } else {
                                        image = Image.getInstance("img/reactivosb.png");
                                    }
                                } catch (BadElementException ex) {
                                    System.out.println("Image BadElementException" + ex);
                                } catch (IOException ex) {
                                    System.out.println("Image IOException " + ex);
                                }
                                tabla.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla.addCell(nu);
                                tabla.getDefaultCell().setColspan(10);
                                tabla.addCell(image);

                                n = j + 21;
                                if (n == datExa.size()) {
                                    tabla.getDefaultCell().setColspan(12);
                                    tabla.getDefaultCell().setRowspan(celVa2);
                                    tabla.addCell(vac[0]);
                                    tabla.getDefaultCell().setRowspan(1);
                                    tabla.getDefaultCell().setColspan(2);
                                }
                                if (n < datExa.size()) {
                                    tabla.getDefaultCell().setColspan(2);
                                    nu = new Paragraph(n + "");
                                    nu.getFont().setSize(10);
                                    tabla.addCell(nu);
                                    tabla.getDefaultCell().setColspan(10);
                                    tabla.addCell(image);
                                }

                            }
                        }
                    }
                }
            } else if (colTabla == 3) {
                String[] vac = new String[2];
                vac[0] = " ";
                vac[1] = " ";
                int celVa = 0, celVa2 = 0;

                if (nPre > 32 && nPre < 41) {
                    celVa2 = 52 - nPre;

                    for (int i = 13; i < datExa.size() - 1; i++) {
                        Image image = null;
                        int h = i + 1;
                        try {
                            if (h % 2 == 0) {
                                image = Image.getInstance("img/reactivosg.png");
                            } else {
                                image = Image.getInstance("img/reactivosb.png");
                            }
                        } catch (BadElementException ex) {
                            System.out.println("Image BadElementException" + ex);
                        } catch (IOException ex) {
                            System.out.println("Image IOException " + ex);
                        }

                        if (i == 13) {
                            image = Image.getInstance("img/reactivosb.png");
                            tabla.getDefaultCell().setColspan(12);
                            tabla.addCell(instrucciones);
                            tabla.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(2);
                            nu = new Paragraph("33");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(12);
                            tabla.getDefaultCell().setRowspan(7);
                            tabla.addCell(image2);
                            tabla.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }
                        h = i + 1;
                        tabla.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla.addCell(nu);
                        tabla.getDefaultCell().setColspan(10);
                        tabla.addCell(image);
                        tabla.getDefaultCell().setColspan(2);
                        h = i + 21;
                        if (h == datExa.size()) {
                            tabla.getDefaultCell().setColspan(12);
                            tabla.getDefaultCell().setRowspan(celVa2);
                            tabla.addCell(vac[0]);
                            tabla.getDefaultCell().setRowspan(1);
                            tabla.getDefaultCell().setColspan(2);
                        } else if (h < datExa.size()) {
                            nu = new Paragraph(h + "");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(2);
                        }
                        if (i == 19) {
                            for (int j = 0; j < 12; j++) {
                                image = null;
                                int n = j + 1;
                                try {
                                    if (n % 2 == 0) {
                                        image = Image.getInstance("img/reactivosg.png");
                                    } else {
                                        image = Image.getInstance("img/reactivosb.png");
                                    }
                                } catch (BadElementException ex) {
                                    System.out.println("Image BadElementException" + ex);
                                } catch (IOException ex) {
                                    System.out.println("Image IOException " + ex);
                                }
                                tabla.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla.addCell(nu);
                                tabla.getDefaultCell().setColspan(10);
                                tabla.addCell(image);

                                n = j + 21;
                                tabla.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla.addCell(nu);
                                tabla.getDefaultCell().setColspan(10);
                                tabla.addCell(image);
                                i = i + 21;
                            }
                        }
                    }
                } else if (nPre < 53) {

                    celVa2 = 52 - nPre;

                    for (int i = 13; i < datExa.size() - 1; i++) {
                        Image image = null;
                        int h = i + 1;
                        try {
                            if (h % 2 == 0) {
                                image = Image.getInstance("img/reactivosg.png");
                            } else {
                                image = Image.getInstance("img/reactivosb.png");
                            }
                        } catch (BadElementException ex) {
                            System.out.println("Image BadElementException" + ex);
                        } catch (IOException ex) {
                            System.out.println("Image IOException " + ex);
                        }

                        if (i == 13) {
                            image = Image.getInstance("img/reactivosb.png");
                            tabla.getDefaultCell().setColspan(12);
                            tabla.addCell(instrucciones);
                            tabla.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(2);
                            nu = new Paragraph("33");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(12);
                            tabla.getDefaultCell().setRowspan(7);
                            tabla.addCell(image2);
                            tabla.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }
                        h = i + 1;
                        tabla.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla.addCell(nu);
                        tabla.getDefaultCell().setColspan(10);
                        tabla.addCell(image);
                        tabla.getDefaultCell().setColspan(2);
                        h = i + 21;
                        if (h < 41) {
                            nu = new Paragraph(h + "");
                            nu.getFont().setSize(10);
                            tabla.addCell(nu);
                            tabla.getDefaultCell().setColspan(10);
                            tabla.addCell(image);
                            tabla.getDefaultCell().setColspan(2);
                        }
                        if (i == 19) {
                            for (int j = 0; j < 12; j++) {
                                image = null;
                                int n = j + 1;
                                try {
                                    if (n % 2 == 0) {
                                        image = Image.getInstance("img/reactivosg.png");
                                    } else {
                                        image = Image.getInstance("img/reactivosb.png");
                                    }
                                } catch (BadElementException ex) {
                                    System.out.println("Image BadElementException" + ex);
                                } catch (IOException ex) {
                                    System.out.println("Image IOException " + ex);
                                }
                                tabla.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla.addCell(nu);
                                tabla.getDefaultCell().setColspan(10);
                                tabla.addCell(image);

                                n = j + 21;
                                tabla.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla.addCell(nu);
                                tabla.getDefaultCell().setColspan(10);
                                tabla.addCell(image);
                                n = j + 41;
                                if (n == datExa.size()) {
                                    tabla.getDefaultCell().setColspan(12);
                                    tabla.getDefaultCell().setRowspan(celVa2);
                                    tabla.addCell(vac[0]);
                                    tabla.getDefaultCell().setRowspan(1);
                                    tabla.getDefaultCell().setColspan(2);
                                }
                                if (n < datExa.size()) {
                                    tabla.getDefaultCell().setColspan(2);
                                    nu = new Paragraph(n + "");
                                    nu.getFont().setSize(10);
                                    tabla.addCell(nu);
                                    tabla.getDefaultCell().setColspan(10);
                                    tabla.addCell(image);
                                }
                                i = i +41; 
                            }
                        }
                    }
                }
            }
            Paragraph foot = new Paragraph("Hoja hecha por Examinator-X®" + 
                    "                 " + "software de PrimeSoft®");
            foot.getFont().setSize(5);
            documento.add(tabla);
            foot.setAlignment(Element.ALIGN_RIGHT);
            documento.add(foot);
            documento.close();
            System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");

            try {
                File path = new File("pdf/" + nombreArchivo + "Exa.pdf");
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: :'v" + documentException);
        }
    }
}
