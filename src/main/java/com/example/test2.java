package com.example;

public class test2 {
    public static void main(String[] args) {
        // 创建一个 800x600 的地图
        ManualMap manualMap = new ManualMap("resouce\\map1.jpg");
        manualMap.printMap("resouce\\Vmap.txt"); // 打印地图（用于调试）

        // 示例：判断某个位置是否可以放置物品
        int x = 100;
        int y = 100;
        if (manualMap.isPositionPlaceable(x, y)) {
            System.out.println("可以放置物品在 (" + x + ", " + y + ")");
        } else {
            System.out.println("无法放置物品在 (" + x + ", " + y + ")");
        }
    }
}
