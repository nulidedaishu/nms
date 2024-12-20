package com.itany.nmms.controller;

import com.itany.mvc.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RequestMapping("/code")
public class CodeController {

    @RequestMapping("/show")
    public void showCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedImage image = new BufferedImage(50, 25, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.fillRect(0, 0, 50, 25);
        Random random = new Random();
        graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        graphics.setFont(new Font("宋体", Font.BOLD + Font.ITALIC, 18));
        String s = "qwertyuiopasdfghjklzxcvbnm1234567890";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            buffer.append(s.charAt(random.nextInt(s.length())));
        }
        String code = buffer.toString();

        request.getSession().setAttribute("code", code);
        System.out.println("验证码:" + code);

        graphics.drawString(code, 5, 18);
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}
