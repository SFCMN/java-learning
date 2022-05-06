package org.sfcmn.basics;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class MyResourceWithAutoCloseable implements AutoCloseable {
  @Override
  public void close() throws Exception {
    System.out.println("Close MyResourceWithAutoCloseable");
  }
}

class MyResourceWithCloseable implements Closeable {
  @Override
  public void close() throws IOException {
    System.out.println("Close MyResourceWithCloseable");
  }
}

class Connection implements AutoCloseable {
  public void sendData() throws Exception {
    throw new Exception("send data");
  }
  @Override
  public void close() throws Exception {
    throw new Exception("close");
  }
}

public class TryWithResources {
  private static void test() throws Exception {
    Connection conn = new Connection();
    //noinspection TryFinallyCanBeTryWithResources
    try {
      conn.sendData();
    } finally {
      conn.close();
    }
  }

  public static void main(String[] args) throws Exception {
    try (
        MyResourceWithAutoCloseable myResourceWithAutoCloseable = new MyResourceWithAutoCloseable();
        MyResourceWithCloseable myResourceWithCloseable = new MyResourceWithCloseable()
    ) {
      System.out.println("Try - MyResourceWithAutoCloseable");
      System.out.println("Try - MyResourceWithCloseable");
    }

    final Scanner scanner = new Scanner(new File("README.md"));
    PrintWriter writer = new PrintWriter(new File("testWrite.txt"));
    try (scanner; writer) {
      while (scanner.hasNext()) {
        writer.print(scanner.nextLine());
      }
    }

    try (
      Connection connection = new Connection();
    ) {
      connection.sendData();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      test();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
