package leetcode;

public class Utf8Validator {
  public static void main(String[] args) {
    int a = 0x11;
    System.out.println("a=" + a);
    int n = 265;
    System.out.println(n & 0B11111111);
    System.out.println(Integer.toBinaryString(13));
  }

  static class Solution {
    public boolean validUtf8(int[] data) {
      if (data == null) {
        return false;
      }

      int mask1 = 1 << 7;
      int mask2 = 1 << 6;
      // numBytesToProcess 有三项用途
      // 0 表示开始检测一个字符的字节
      // 存储一个字符的字节数
      // 校验一个字符的过程中，表示还有几个字节要处理
      int numBytesToProcess = 0;
      for (int i = 0; i < data.length; i++) {
        if (numBytesToProcess == 0) { // 开始校验一个字符的第一个字节
          // 计算在低 8 位从高位开始连续有几个 1
          int mask = 1 << 7;
          while ((mask & data[i]) != 0) {
            numBytesToProcess += 1;
            mask = mask >> 1;
          }

          // 当前字符只有一个字节。已校验完毕。
          if (numBytesToProcess == 0) {
            continue;
          }

          // 一个合法的 UTF-8 字符长度为 1-4 字节。若只有一字节，则其高位为 0
          if (numBytesToProcess > 4 || numBytesToProcess == 1) {
            return false;
          }
        } else { // 校验一个字符的剩余字节
          if (!((mask1 & data[i]) != 0 && (mask2 & data[i]) == 0)) {
            return false;
          }
        }

        // 校验完一个字节后，记得将 numBytesOfCurrChar 减一
        numBytesToProcess = numBytesToProcess - 1;
      }

      // 针对情形：给定的数组没有包含一个字符的完整字节
      return numBytesToProcess == 0;
    }
  }

  static class SolutionV2 {
    public boolean validUtf8(int[] data) {
      if (data == null) {
        return false;
      }

      int mask = 0B11111111; // 掩码，用于取得一个数的低 8 位
      int numBytesToProcess = 0;
      for (int i = 0; i < data.length; i++) {
        String binary = Integer.toBinaryString(data[i] & mask);
        String paddedBin =
            new StringBuilder(8)
                .append("00000000".substring(binary.length()))
                .append(binary)
                .toString();
        if (numBytesToProcess == 0) { // 开始处理一个 UTF-8 字符
          for (int j = 0; j < paddedBin.length() && paddedBin.charAt(j) == '1'; j++) {
            numBytesToProcess++;
          }

          if (numBytesToProcess == 0) { // 只有一个字节
            continue;
          }

          // 一个合法的 UTF-8 字符长度为 1-4 字节。若只有一字节，则其高位为 0
          if (numBytesToProcess > 4 || numBytesToProcess == 1) {
            return false;
          }
        } else { // 校验剩余字节
          if (!(paddedBin.charAt(0) == '1' && paddedBin.charAt(1) == '0')) {
            return false;
          }
        }

        // 校验完一个字节后，记得将 numBytesOfCurrChar 减一
        numBytesToProcess--;
      }

      // 针对情形：给定的数组没有包含一个字符的完整字节
      return numBytesToProcess == 0;
    }
  }
}
