/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examinador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Alexis
 */
public class hojaDeRespuestas {
    private String nombrePDF, materia, fecha, maestro, titulo;
    private int numReactivos=85;
    public hojaDeRespuestas(String nombreExamen, String maest, int numPreguntas, String mater, String fech){
        nombrePDF = nombreExamen + "_" + maest + "_" + fech;
        titulo = nombreExamen;
        fecha = fech;
        materia = mater;
        maestro = maest;
    }
    public void generarHoja(){
        try{
            Document documento = new Document(PageSize.LETTER,10,10,30,30);
            try {
                PdfWriter.getInstance(documento, new FileOutputStream(new File(nombrePDF) + ".pdf"));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("(No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            documento.open();
            documento.addAuthor("Examinator-X");
            documento.addTitle(nombrePDF);
            String fechaS = "Fecha: " + fecha + System.lineSeparator();
            String materiaS =  "Materia: " + materia + System.lineSeparator();
            String mano1 = "Nombre:______________________________________________"
                    + "                " + "Grupo:___________" + System.lineSeparator();
            String mano2 = "Boleta:________________________" + "                           "
                    + "Profesor: "+ maestro + System.lineSeparator();
            String instrucciones = "INSTRUCCIONES: " + System.lineSeparator()
                    + "1.- Usa solo lápiz. No uses boligrafo ni macador." + System.lineSeparator() 
                    + "2.- Marca con intensidad la respuesta que consideres correcta utilizando la siguiente marca:"+ System.lineSeparator()
                    + "3.- Si te equivocas borra completamente. No taches. No hagas marcas fuera de los recuadros." + System.lineSeparator()
                    + "4.- Rellena la parte de números con tu identificador." + System.lineSeparator()+ System.lineSeparator();
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.add(fechaS);
            parrafo.add(materiaS);
            parrafo.add(mano1);
            parrafo.add(mano2);
            parrafo.add(instrucciones);
            documento.add(parrafo);
            PdfPTable tabla = new PdfPTable(2), tabla1 = new PdfPTable(1), tabla2 = new PdfPTable(1), 
                    tabla3 = new PdfPTable(1), tabla4 = new PdfPTable(1), tabla5 = new PdfPTable(2),
                    tabla6 = new PdfPTable(2);
            tabla.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla.setWidthPercentage(100);
            tabla1.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla2.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla3.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla4.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla5.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla6.getDefaultCell().setBorderColor(BaseColor.WHITE);
            tabla5.getDefaultCell().setRowspan(10);
            tabla5.getDefaultCell().setColspan(2);
            tabla5.addCell(iden());
            tabla5.getDefaultCell().setColspan(1);
            tabla5.getDefaultCell().setRowspan(1);
            if(numReactivos<18){
                for(int i = 0; i<numReactivos; i++){
                    tabla1.addCell(reactivos(i+1));
                }
            } else {
                if(numReactivos<36){
                    for(int i = 0; i<18; i++){
                        tabla1.addCell(reactivos(i+1));
                    }
                    for(int i = 0; i<numReactivos-18; i++){
                        tabla2.addCell(reactivos(i+19));
                    }
                    for(int i = 0; i<36-numReactivos; i++){
                        tabla2.addCell(vacios());
                    }
                } else {
                    if(numReactivos<65){
                        for(int i = 0; i<18; i++){
                            tabla1.addCell(reactivos(i+1));
                            tabla2.addCell(reactivos(i+19));
                        }
                        for(int i = 0; i<numReactivos-36; i++){
                            tabla3.addCell(reactivos(i+37));
                        }
                        for(int i = 0; i<65-numReactivos; i++){
                            tabla3.addCell(vacios());
                        }
                        
                    } else {
                       if(numReactivos<93){
                            for(int i = 0; i<18; i++){
                                tabla1.addCell(reactivos(i+1));
                                tabla2.addCell(reactivos(i+19));
                            }
                            for(int i = 0; i<28; i++){
                                tabla3.addCell(reactivos(i+37));
                            }
                            for(int i = 0; i<numReactivos-64; i++){
                                tabla4.addCell(reactivos(i+65));
                            }
                            for(int i = 0; i<93-numReactivos; i++){
                        tabla4.addCell(vacios());
                    }
                        } else {
                           System.out.println("No mas de 92");
                       }  
                    }
                }
            }
//            for(int i = 0; i<18; i++){
//                tabla1.addCell(reactivos(i+1));
//                tabla2.addCell(reactivos(i+19));
//            }
//            for(int i = 0; i<28; i++){
//                tabla3.addCell(reactivos(i+37));
//                tabla4.addCell(reactivos(i+65));
//            }
            tabla5.addCell(tabla1);
            tabla5.addCell(tabla2);
            tabla6.addCell(tabla3);
            tabla6.addCell(tabla4);
            tabla.addCell(tabla5);
            tabla.addCell(tabla6);
            documento.add(tabla);
            documento.close();
            System.out.println("Your PDF file has been generated!(¡Se ha generado tu hoja PDF!");

            try {
                File path = new File(nombrePDF + ".pdf");
                Desktop.getDesktop().open(path);
            } catch (IOException ex) {
                System.out.println("No lo abrio :'v");
            }
            
        }catch(DocumentException documentException){
            
        }
    }
    
    private PdfPTable reactivos(int num){
        PdfPTable ret = new PdfPTable(19);
        ret.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        ret.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        ret.getDefaultCell().setColspan(3);
        ret.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        ret.addCell(num+"");
        ret.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        ret.getDefaultCell().setColspan(1);
        ret.getDefaultCell().setBorderColor(BaseColor.WHITE);
        ret.addCell("");
        
        String array[] = { "A", "B", "C", "D"};
        for(int i = 0; i<4; i++){
            ret.getDefaultCell().setBorderColor(BaseColor.BLACK);
            ret.getDefaultCell().setColspan(3);
            ret.addCell(array[i]);
            ret.getDefaultCell().setColspan(1);
            ret.getDefaultCell().setBorderColor(BaseColor.WHITE);
            ret.addCell("");
        }
        return ret;
    }
    
    private PdfPTable iden(){
        PdfPTable ret = new PdfPTable(1);
        ret.getDefaultCell().setBorderColor(BaseColor.WHITE);
        for(int i = 0; i<10; i++){
            ret.addCell(numeros());
        }
        return ret;
    }
    
    private PdfPTable numeros(){
        PdfPTable ret = new PdfPTable(40);
        String array[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        ret.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        ret.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        for(int i = 0; i<10; i++){
            ret.getDefaultCell().setBorderColor(BaseColor.BLACK);
            ret.getDefaultCell().setColspan(3);
            ret.addCell(array[i]);
            ret.getDefaultCell().setColspan(1);
            ret.getDefaultCell().setBorderColor(BaseColor.WHITE);
            ret.addCell("");
        }
        return ret;
    }
    
   private PdfPTable vacios(){
       PdfPTable ret = new PdfPTable(1);
       ret.getDefaultCell().setBorderColor(BaseColor.WHITE);
       ret.addCell("");
       return ret;
   }
}
