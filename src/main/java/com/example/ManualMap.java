package com.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
public class ManualMap {
    public int[][] grid;
    private BufferedImage image;

    public ManualMap(String filePath) {
        System.out.println("123");
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("123");
        int width=1050;
        int height=600;
        grid = new int[height][width];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
                    grid[y][x]=0;
                }
                else
                {
                    int pixel = image.getRGB(x, y);
                    Color color = new Color(pixel, true);

                    // 定义一个阈值，比如说颜色接近绿色（可以放置物品）
                    int greenThreshold = 230;
                    grid[y][x] = (color.getGreen() > greenThreshold && color.getRed() > greenThreshold && color.getBlue() > greenThreshold) ? 1 : 0;
                }

            }
        }
    
    }

    // 初始化地图，可以手动设置哪些位置可以放置物品
    

    // 检查某个位置是否可以放置物品
    public boolean isPositionPlaceable(int x, int y) {
        return grid[y][x] == 1; // 注意，这里 x 表示列，y 表示行
    }

    // 打印地图（用于调试）
    public void printMap(String filePath) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (int[] row : grid) {
                for (int cell : row) {
                    out.print(cell);
                }
                out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取地图的行数

    // 获取地图
    public int[][] getGrid() {
        return grid;
    }
}
