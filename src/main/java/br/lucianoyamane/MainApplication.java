package br.lucianoyamane;

import br.lucianoyamane.utils.ColorsUtils;
import br.lucianoyamane.utils.CompressJPGUtils;
import br.lucianoyamane.utils.ScalrUtils;

import java.io.File;

public class MainApplication {

    public static void main(String[] args) {
        String[] files = new String[] { "mustang"};
        for(String file : files) {
            runCompress(file);
            runBlackWhite(file);
            runResizeScalr(file);
        }
    }

    private static void runResizeScalr(String sourceFile) {
        String resourcePath = new File("src/main/resources/images").getAbsolutePath();
        File file = new File(resourcePath + File.separator + sourceFile + ".jpg");
        File fileResult = new File(resourcePath + File.separator + sourceFile + "_scalr.jpg");

        ScalrUtils.resizeImage(file, fileResult);
    }

    private static void runBlackWhite(String sourceFile) {
        String resourcePath = new File("src/main/resources/images").getAbsolutePath();
        File file = new File(resourcePath + File.separator + sourceFile + ".jpg");
        File fileResult = new File(resourcePath + File.separator + sourceFile + "_bw.jpg");

        ColorsUtils.blackOrWhite(file, fileResult);
    }

    private static void runCompress(String sourceFile) {
        String resourcePath = new File("src/main/resources/images").getAbsolutePath();

        File file = new File(resourcePath + File.separator + sourceFile + ".jpg");
        File fileResult = new File(resourcePath + File.separator + sourceFile + "_file.jpg");

        CompressJPGUtils.compressImageJpg(0.3f, file, fileResult);

        byte[] fileByte = CompressJPGUtils.convertFileByteArray(resourcePath + File.separator + sourceFile + ".jpg");
        byte[] resultado = CompressJPGUtils.compressImageJpgByte(0.3f, fileByte);
        CompressJPGUtils.convertByteArrayFile(resultado, resourcePath + File.separator + sourceFile + "_byte.jpg");
    }
}
