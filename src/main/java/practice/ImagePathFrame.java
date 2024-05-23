package practice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImagePathFrame extends JFrame {
    private JLabel imageLabel;

    public ImagePathFrame() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null); // 使用絕對佈局

        // 初始化圖片
        ImageIcon image = new ImageIcon("resouce\\P.jpg");
        imageLabel = new JLabel(image);
        imageLabel.setBounds(100, 100, image.getIconWidth(), image.getIconHeight());
        this.add(imageLabel);

        moveImage(); // 呼叫移動方法

        this.setVisible(true);
    }

    public void moveImage() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新圖片位置
                int x = imageLabel.getX() + 5;
                int y = imageLabel.getY();
                imageLabel.setLocation(x, y);

                // 檢查碰撞並更換圖片
                if (collisionDetected()) {
                    imageLabel.setIcon(new ImageIcon("resouce\\G.jpg"));
                }
            }
        });
        timer.start();
    }

    private boolean collisionDetected() {
        // 這裡添加碰撞檢測的邏輯，根據實際需要調整
        
        return false; // 假設這裡暫時沒有碰撞發生
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImagePathFrame(); // 創建並顯示窗口
            }
        });
    }
}
