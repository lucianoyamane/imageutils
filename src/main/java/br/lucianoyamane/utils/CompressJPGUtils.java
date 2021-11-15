package br.lucianoyamane.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class CompressJPGUtils {

    public static byte[] compressImageJpgByte(float imageQuality, byte[] source) {
        byte[] resultado = null;
        OutputStream outputStream = null;
        ImageWriter imageWriter = null;
        ImageOutputStream imageOutputStream = null;

        try {
            outputStream = new ByteArrayOutputStream();
            imageWriter = getImageWriterByFormatName("jpg");
            imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            imageWriter.setOutput(imageOutputStream);

            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(imageQuality);

            imageWriter.write(null, new IIOImage(getBufferedImageFromByte(source), null, null), imageWriteParam);
            resultado = ((ByteArrayOutputStream)outputStream).toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{

            try {
                outputStream.close();
                imageOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageWriter != null) {
                imageWriter.dispose();
            }
        }


        return resultado;
    }


    public static void compressImageJpg(float imageQuality, File source, File result) {
        OutputStream outputStream = null;
        ImageOutputStream imageOutputStream = null;
        ImageWriter imageWriter = null;

        try {
            outputStream = new FileOutputStream(result);

            imageWriter = getImageWriterByFormatName("jpg");
            imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            imageWriter.setOutput(imageOutputStream);

            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();

            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality(imageQuality);

            imageWriter.write(null, new IIOImage(getBufferedImageFromFile(source), null, null), imageWriteParam);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                imageOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageWriter != null) {
                imageWriter.dispose();
            }
        }
    }

    public static void convertByteArrayFile(byte[] resultado, String path) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(resultado);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static byte[] convertFileByteArray(String filePath) {
        byte[] resultado = null;
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            resultado = buffer.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return resultado;
    }

    private static BufferedImage getBufferedImageFromByte(byte[] source) {
        InputStream inputStream = null;
        BufferedImage bufferedImage = null;
        try {
            inputStream = new ByteArrayInputStream(source);
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bufferedImage.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedImage;
    }


    private static BufferedImage getBufferedImageFromFile(File file) {
        InputStream inputStream = null;
        BufferedImage bufferedImage = null;
        try {
            inputStream = new FileInputStream(file);
            bufferedImage = ImageIO.read(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bufferedImage.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bufferedImage;
    }

    private static ImageWriter getImageWriterByFormatName(String format) {
        if (format == null) {
            throw new IllegalStateException("Format null!");
        }
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName(format);
        if (!imageWriters.hasNext())
            throw new IllegalStateException("Writers Not Found!!");

        return imageWriters.next();
    }
}
