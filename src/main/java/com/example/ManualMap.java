package com.example;

import javax.imageio.ImageIO;

import javafx.scene.layout.StackPane;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
public class ManualMap {
    public static int[][] grid;
    public static int[][] original_grid;
    private BufferedImage image;

    public ManualMap(String filePath) {
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width=1050;
        int height=600;
        grid = new int[height][width];
        original_grid = new int[height][width];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
                    grid[y][x]=0;
                    original_grid[y][x]=0;
                }
                else
                {
                    int pixel = image.getRGB(x, y);
                    Color color = new Color(pixel, true);

                    // 定义一个阈值，比如说颜色接近绿色（可以放置物品）
                    int greenThreshold = 230;
                    int blueThreshold = 230;
                    //grid[y][x] = (color.getGreen() > greenThreshold && color.getRed() > greenThreshold && color.getBlue() > greenThreshold) ? 1 : 0;
                    if (color.getGreen() > greenThreshold && color.getRed() > greenThreshold && color.getBlue() > greenThreshold){
                        grid[y][x]=1;
                        original_grid[y][x]=1;
                    }
                    else if (color.getGreen() <10 && color.getRed() < 10 && color.getBlue() > blueThreshold){
                        grid[y][x]=2;
                        original_grid[y][x]=2;
                    }
                    else{
                        grid[y][x]=0;
                        original_grid[y][x]=0;
                    }
                }
            }
        }
    }

    // 初始化地图，可以手动设置哪些位置可以放置物品
    

    // 检查某个位置是否可以放置物品
    public boolean isPositionPlaceable(String towerType,int x, int y) {
        if (towerType.equals("Battleship")) {
            return grid[y][x] == 2;
        }
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
    public static void restoreMap(tower newTower) {
        for(int y=0;y<newTower.imageheight;y++)
        {
            for(int x=0;x<newTower.imagewidth;x++)
            {
                int y1=(int)newTower.getTowerPane().getLayoutY()+ (int)newTower.getTowerImageRange().getFitHeight()/2 -(int)newTower.getTowerImageView().getFitHeight()/2 + y;
                int x1=(int)newTower.getTowerPane().getLayoutX()+ (int)newTower.getTowerImageRange().getFitWidth()/2 -(int)newTower.getTowerImageView().getFitWidth()/2 + x;
                if(y1>0&&x1>0&&y1<grid.length&&x1<grid[0].length)
                    grid[y1][x1] =original_grid[y1][x1];
            }
        }
    }

    // 获取地图的行数

    // 获取地图
    public int[][] getGrid() {
        return grid;
    }
}
