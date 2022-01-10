package leetcode;

import java.util.LinkedList;
import java.util.List;

public class RestoreIpAddress {
  static class Solution {

    static final int SEG_COUNT = 4;
    int[] segments = new int[SEG_COUNT];
    List<String> res = new LinkedList<>();

    public List<String> restoreIpAddresses(String s) {
      dfs(s, 0, 0);
      return res;
    }

    void dfs(String s, int segId, int segStart) {
      if (segId == SEG_COUNT) {
        // 如果找到了 4 个 ip-seg，且遍历完了字符串，那么就找到了一个答案
        if (segStart == s.length()) {
          StringBuilder ip = new StringBuilder(12);
          for (int i = 0; i < segments.length; i++) {
            ip.append(segments[i]);
            if (i < segments.length - 1) {
              ip.append(".");
            }
          }
          res.add(ip.toString());
        } else {
          // 若找到了 4 个 ip-seg，还未到达字符串末尾，则 s 是一个无效的 ip 地址串
          return;
        }
      }

      // Case: s 较短，还未得到 4 个 ip-seg 就已经走到字符串末尾了
      if (segStart == s.length()) {
        return;
      }

      if (s.charAt(segStart) == '0') {
        // 如果 start 位置的字符为 0，则该段 ip 只能是 0，因为不允许有前导 0
        // 这样这一步就只有一个选择了。
        segments[segId] = 0;
        dfs(s, segId + 1, segStart + 1);
      } else {
        // 若不为 0，则这一步最多有 3 个选择：[start, start], [start, start+1], [start, start+2]
        int addr = 0;
        for (int segEnd = segStart; segEnd < s.length(); segEnd++) {
          // 这里挺巧妙。如果从 start 开始的三个数为：235，
          // 那么三个选择分别是 2，23，235。每次在低位追加一个数，
          // 使得 2 的权重依次为 10^0，10^1，10^2
          addr = addr * 10 + (s.charAt(segEnd) - '0');
          if (addr > 0 && addr <= 0xFF) {
            segments[segId] = addr;
            dfs(s, segId + 1, segEnd + 1);
          } else {
            break;
          }
        }
      }
    }
  }
}
