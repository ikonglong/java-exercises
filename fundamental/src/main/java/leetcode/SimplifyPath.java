package leetcode;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class SimplifyPath {

  public static void main(String[] args) {
    MySolution s = new MySolution();
    System.out.println(s.simplifyPath("/"));
    System.out.println(s.simplifyPath("/."));
    System.out.println(s.simplifyPath("/.."));
    System.out.println(s.simplifyPath("/../"));
    System.out.println(s.simplifyPath("/a/./b/../../c/"));
    System.out.println(s.simplifyPath("/home//foo/"));
  }

  static class MySolution {
    public String simplifyPath(String path) {
      Deque<String> que = new LinkedList<>();
      int sepIndex = -1, start = -1, end = -1;
      for (int i = 0; i < path.length(); i++) {
        char c = path.charAt(i);
        if (start == -1 && c != '/' && c != ' ') {
          start = end = i;
        }
        if (start != -1 && c != '/' && c != ' ') {
          end = i;
        }
        if (c == '/' || i == path.length() - 1) {
          if (start != -1) {
            String fileOrDir = path.substring(start, end + 1);
            if (fileOrDir.equals(".")) {
              // not add to que
            } else if (fileOrDir.equals("..")) {
              if (que.size() >= 1) {
                que.removeFirst();
              }
            } else {
              que.addFirst(fileOrDir);
            }
            start = end = -1;
          }
        }
      }
      StringBuilder pathBuild = new StringBuilder(que.size() * 10);
      pathBuild.append("/");
      for (Iterator<String> it = que.descendingIterator(); it.hasNext(); ) {
        pathBuild.append(it.next());
        if (it.hasNext()) {
          pathBuild.append("/");
        }
      }
      return pathBuild.toString();
    }
  }
}
