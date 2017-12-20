package examinador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class examenPDF {
    private String nombreArchivo;
    int colTabla;
    private Paragraph nu;
    private static final Font raya = FontFactory.getFont(FontFactory.COURIER_BOLD);
     ArrayList <constructor> datExa = new ArrayList<>();
    private static final Font Tit = FontFactory.getFont(FontFactory.COURIER, 22, Font.BOLDITALIC);
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    
    public void AbrirArchivo() throws IOException {
        try
        {
            FileInputStream fis = new FileInputStream("dat/" + nombreArchivo + ".dat");
            ObjectInputStream entrada = new ObjectInputStream (fis);
            datExa = (ArrayList<constructor>)entrada.readObject();
            entrada.close();
        }
        catch(FileNotFoundException e){
        }
        catch(IOException | ClassNotFoundException e){
        }
    }
    public void crearPDF(File pdfNewFile) throws IOException{
        constructor con = new constructor();
        try{
            Document documento = new Document(PageSize.LETTER);
            try {
                PdfWriter.getInstance(documento, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            documento.open();
            documento.addAuthor("Examinator-X");
            
            AbrirArchivo();
            int cuanNor = 0, cuanCol = 0,t = 0, cuantos = datExa.size();
            int cuanRel[] = new int[cuantos];
            
            for (int i = 0; i < cuantos; i++) {
                    cuanRel[i] = datExa.get(i).getNumCol();
            }
            documento.addTitle(nombreArchivo+"Exa");
            Image logoBatiz = null, logoIpn = null;
            try {
               
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
            Paragraph parraf = new Paragraph();
            parraf.setAlignment(Element.ALIGN_CENTER);
            int donde = 0;
            for (int i = 0; i < datExa.size(); i++) {
                if (datExa.get(i).getMateria() != null) {
                    donde = i;
                }
            }
            logoBatiz.setAbsolutePosition(50, altura - 80);
            logoBatiz.scaleAbsolute(50, 50);
            logoIpn.setAbsolutePosition(ancho - 95, altura - 80);
            logoIpn.scaleAbsolute(35, 50);
            documento.add(logoBatiz);
            documento.add(logoIpn);
            Chunk titulo = new Chunk(nombreArchivo + System.lineSeparator());
            String escuela = "INSTITUTO POLITÉCNICO NACIONAL" + System.lineSeparator()
                    + "CECyT 9 \"Juan de Dios Batiz\"" + System.lineSeparator()
                    + System.lineSeparator();
            String fecha = "Fecha: " + datExa.get(donde).getFecha() + System.lineSeparator();
            String periodo = "Periodo: " + datExa.get(donde).getPeriodo() + ""
                    + "\t" + "                             Materia: "
                    + datExa.get(donde).getMateria() + System.lineSeparator();
            String Instru = "Responde en la hoja de respuestas rellenando comple"
                    + "tamente el ovalo, sin tachaduras ni borrones" + System.lineSeparator()
                    + System.lineSeparator();
            titulo.setFont(Tit);
            parraf.add(escuela);
            parraf.add(titulo);
            parraf.add(fecha);
            parraf.add(periodo);
            parraf.add(Instru);
            documento.add(parraf);
            PdfPTable tabla[] = new PdfPTable[cuantos];
            String col1[][] = new String[cuantos][20];
            String col2[][] = new String[cuantos][20];
            String va[][] = new String[cuantos][20];
            String res[][] = new String[cuantos][4];
            String enunciado[] = new String[cuantos];
            Paragraph parrafo[] = new Paragraph[cuantos];
            for (int i = 1; i < cuantos; i++) {
                parrafo[i] = new Paragraph();
                if(datExa.get(i).getNumCol() != 0){
                    
                    enunciado[i] = datExa.get(i).getPregunta();
                    parrafo[i].add(i + ".- " + enunciado[i] + System.lineSeparator());
                    int r = cuanRel[i];
                    tabla[i] = new PdfPTable(3);
                    tabla[i].getDefaultCell().setBorderColor(BaseColor.WHITE);
                    for (int j = 0; j < r; j++) {
                        col1[i] = datExa.get(i).getOpcCol();
                        col2[i] = datExa.get(i).getRelCol();
                        tabla[i].addCell(col1[i][j]);
                        tabla[i].addCell(va[i][j]);
                        tabla[i].addCell(col2[i][j]);
                        
                    }
                    parrafo[i].add(tabla[i]);
                    for (int j = 0; j < 4; j++) {
                        res[i] = datExa.get(i).getOpc();
                        parrafo[i].add(res[i][j] + System.lineSeparator());
                    }
                    parrafo[i].add(System.lineSeparator());
                }
                else{
                  
                  enunciado[i] = datExa.get(i).getPregunta();
                  parrafo[i].add(i + ".- " + enunciado[i]+ System.lineSeparator());
                    for (int j = 0; j < 4; j++) {
                        res[i] = datExa.get(i).getOpc();
                        
                        parrafo[i].add(res[i][j]+ System.lineSeparator());
                    }
                        
                    parrafo[i].add(System.lineSeparator());
                }
                documento.add(parrafo[i]);
                
            }
            documento.newPage();
            int nPre = datExa.size() - 1;
            if (datExa.size() < 14) {
                colTabla = 1;
            } else if (datExa.size() < 34) {
                colTabla = 2;
            } else if (datExa.size() < 54) {
                colTabla = 3;
            }

            Image rec = null;
            try {
                rec = Image.getInstance("img/rectangulo2.png");
                logoBatiz = Image.getInstance("img/logoBatiz.png");
                logoIpn = Image.getInstance("img/logoIpn.png");
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" + ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " + ex);
            }
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
            
            Paragraph parrafo2 = new Paragraph();
            parrafo2.setAlignment(Element.ALIGN_CENTER);
            for (int i = 0; i < datExa.size(); i++) {
                if (datExa.get(i).getMateria() != null) {
                    donde = i;
                }
            }
            
            String mano1 = "Nombre:______________________________________________"
                    + "                " + "Grupo:___________" + System.lineSeparator();
            String mano2 = "Boleta:_________________" + "                           "
                    + "Profesor: "
                    + "________________" + System.lineSeparator()
                    + System.lineSeparator();
            titulo.setFont(Tit);

            parrafo2.add(escuela);
            parrafo2.add(titulo);
            parrafo2.add(fecha);
            parrafo2.add(periodo);
            float ta = parrafo2.getFont().getSize();
            parrafo2.getFont().setSize(17);
            parrafo2.add(mano1);
            parrafo2.getFont().setSize(ta);
            parrafo2.add(mano2);
            parrafo2.setFont(Tit);
            documento.add(parrafo2);

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
            PdfPTable tabla2 = new PdfPTable(12 * colTabla);

            tabla2.setWidthPercentage((float) 33 * colTabla);
            tabla2.setHorizontalAlignment(Element.ALIGN_LEFT);
            tabla2.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla2.getDefaultCell().setBorderColor(BaseColor.WHITE);
            if (colTabla == 1) {
                tabla2.getDefaultCell().setColspan(12);
                tabla2.addCell(instrucciones);
                tabla2.getDefaultCell().setRowspan(7);
                tabla2.addCell(image2);
                tabla2.getDefaultCell().setRowspan(1);

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
                    tabla2.getDefaultCell().setColspan(2);

                    nu = new Paragraph(h + "");
                    nu.getFont().setSize(15);
                    tabla2.addCell(nu);
                    tabla2.getDefaultCell().setColspan(10);
                    tabla2.addCell(image);

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
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.addCell(instrucciones);
                            tabla2.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.getDefaultCell().setRowspan(7);
                            tabla2.addCell(image2);
                            tabla2.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }

                        tabla2.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla2.addCell(nu);
                        tabla2.getDefaultCell().setColspan(10);
                        tabla2.addCell(image);
                        if (i == datExa.size() - 2) {
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.getDefaultCell().setRowspan(celVa2);
                            tabla2.addCell(vac[0]);
                            tabla2.getDefaultCell().setRowspan(1);
                            tabla2.getDefaultCell().setColspan(2);
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
                        tabla2.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla2.addCell(nu);
                        tabla2.getDefaultCell().setColspan(10);
                        tabla2.addCell(image);

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
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.addCell(instrucciones);
                            tabla2.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.getDefaultCell().setRowspan(7);
                            tabla2.addCell(image2);
                            tabla2.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }

                        tabla2.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla2.addCell(nu);
                        tabla2.getDefaultCell().setColspan(10);
                        tabla2.addCell(image);
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
                                tabla2.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla2.addCell(nu);
                                tabla2.getDefaultCell().setColspan(10);
                                tabla2.addCell(image);

                                n = j + 21;
                                if (n == datExa.size()) {
                                    tabla2.getDefaultCell().setColspan(12);
                                    tabla2.getDefaultCell().setRowspan(celVa2);
                                    tabla2.addCell(vac[0]);
                                    tabla2.getDefaultCell().setRowspan(1);
                                    tabla2.getDefaultCell().setColspan(2);
                                }
                                if (n < datExa.size()) {
                                    tabla2.getDefaultCell().setColspan(2);
                                    nu = new Paragraph(n + "");
                                    nu.getFont().setSize(10);
                                    tabla2.addCell(nu);
                                    tabla2.getDefaultCell().setColspan(10);
                                    tabla2.addCell(image);
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
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.addCell(instrucciones);
                            tabla2.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(2);
                            nu = new Paragraph("33");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.getDefaultCell().setRowspan(7);
                            tabla2.addCell(image2);
                            tabla2.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }
                        h = i + 1;
                        tabla2.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla2.addCell(nu);
                        tabla2.getDefaultCell().setColspan(10);
                        tabla2.addCell(image);
                        tabla2.getDefaultCell().setColspan(2);
                        h = i + 21;
                        if (h == datExa.size()) {
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.getDefaultCell().setRowspan(celVa2);
                            tabla2.addCell(vac[0]);
                            tabla2.getDefaultCell().setRowspan(1);
                            tabla2.getDefaultCell().setColspan(2);
                        } else if (h < datExa.size()) {
                            nu = new Paragraph(h + "");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(2);
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
                                tabla2.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla2.addCell(nu);
                                tabla2.getDefaultCell().setColspan(10);
                                tabla2.addCell(image);

                                n = j + 21;
                                tabla2.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla2.addCell(nu);
                                tabla2.getDefaultCell().setColspan(10);
                                tabla2.addCell(image);
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
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.addCell(instrucciones);
                            tabla2.getDefaultCell().setColspan(2);
                            nu = new Paragraph("13");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(2);
                            nu = new Paragraph("33");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(12);
                            tabla2.getDefaultCell().setRowspan(7);
                            tabla2.addCell(image2);
                            tabla2.getDefaultCell().setRowspan(1);
                            image = Image.getInstance("img/reactivosg.png");
                        }
                        h = i + 1;
                        tabla2.getDefaultCell().setColspan(2);
                        nu = new Paragraph(h + "");
                        nu.getFont().setSize(10);
                        tabla2.addCell(nu);
                        tabla2.getDefaultCell().setColspan(10);
                        tabla2.addCell(image);
                        tabla2.getDefaultCell().setColspan(2);
                        h = i + 21;
                        if (h < 41) {
                            nu = new Paragraph(h + "");
                            nu.getFont().setSize(10);
                            tabla2.addCell(nu);
                            tabla2.getDefaultCell().setColspan(10);
                            tabla2.addCell(image);
                            tabla2.getDefaultCell().setColspan(2);
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
                                tabla2.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla2.addCell(nu);
                                tabla2.getDefaultCell().setColspan(10);
                                tabla2.addCell(image);

                                n = j + 21;
                                tabla2.getDefaultCell().setColspan(2);
                                nu = new Paragraph(n + "");
                                nu.getFont().setSize(10);
                                tabla2.addCell(nu);
                                tabla2.getDefaultCell().setColspan(10);
                                tabla2.addCell(image);
                                n = j + 41;
                                if (n == datExa.size()) {
                                    tabla2.getDefaultCell().setColspan(12);
                                    tabla2.getDefaultCell().setRowspan(celVa2);
                                    tabla2.addCell(vac[0]);
                                    tabla2.getDefaultCell().setRowspan(1);
                                    tabla2.getDefaultCell().setColspan(2);
                                }
                                if (n < datExa.size()) {
                                    tabla2.getDefaultCell().setColspan(2);
                                    nu = new Paragraph(n + "");
                                    nu.getFont().setSize(10);
                                    tabla2.addCell(nu);
                                    tabla2.getDefaultCell().setColspan(10);
                                    tabla2.addCell(image);
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
            documento.add(tabla2);
            foot.setAlignment(Element.ALIGN_RIGHT);
            documento.add(foot);

            documento.close();
            System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");
            try {
                File path = new File ("pdf/"+nombreArchivo+"Exa.pdf");
                Desktop.getDesktop().open(path);
            }   
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (DocumentException documentException) {
            System.out.println("Se ha producido un error al generar un documento: :'v" + documentException);
            
        }
    }
}
