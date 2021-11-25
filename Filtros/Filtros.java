package Filtros;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Filtros {

    public static BufferedImage pCinza(BufferedImage img) {
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ColorConvertOp op = new ColorConvertOp(img.getColorModel().getColorSpace(), ColorSpace.getInstance(ColorSpace.CS_GRAY),  null);
        op.filter(img, out);
        return out;
    }

    public static BufferedImage binario(BufferedImage img){
        BufferedImage result = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(img, 0, 0, Color.WHITE, null);

        return result;
    }

    public static BufferedImage gaussiano(BufferedImage img){
        float[] aux = {1/16f, 2/16f, 1/16f, 2/16f, 4/16f, 2/16f, 1/16f, 2/16f, 1/16f};
        return aplicarFiltro(img, aux);
    }

    public static BufferedImage laplaciano(BufferedImage img){
        float[] aux = {-2f, -1f, 0f, -1f, 1f, 1f, 0f, 1f, 2f};
        return aplicarFiltro(img, aux);
    }


    public static BufferedImage aplicarFiltro(BufferedImage img, float[] filtro){
        int altura = img.getHeight();
        int largura = img.getWidth();

        BufferedImage resultado =  new BufferedImage(largura, altura, img.getType());

        Kernel kernel = new Kernel(3,3, filtro);
        ConvolveOp cop = new ConvolveOp(kernel,
                ConvolveOp.EDGE_NO_OP,
                null);
        cop.filter(img, resultado);

        return resultado;
    }
}
