package com.atguigu;

import nonapi.io.github.classgraph.scanspec.ScanSpec;
import org.apache.tomcat.util.bcel.Const;

import java.util.Scanner;




public class sort {
    public static final int N=20;
    public static boolean st[] =new boolean[N];
    public static boolean dx[] = new boolean[N];
    public static boolean udx[] = new boolean[N];
    public static char path[][] =new char[N][N];
    public static int n;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                path[i][j] = '.';
            }
        }


        dfs(0);
    }
    public static void dfs(int u)
    {
          if(u==n)
          {
              for (int i = 0; i < n; i++) {
                  for (int j = 0; j <n; j++) {
                      System.out.printf(path[i][j]+"");
                  }
                  System.out.println();
              }
              System.out.println();
          }

        for (int i = 0; i < n; i++) {
            if(!st[i]&&!dx[u+i]&&!udx[u-i+n])
            {
                st[i] = dx[u+i] = udx[u-i+n] =true;
                path[u][i] = 'Q';

                dfs(u+1);
                st[i] = dx[u+i] = udx[u-i+n] =false;
                path[u][i]='.';

            }

        }


    }

}













