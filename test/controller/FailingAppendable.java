package controller;

import java.io.IOException;

/**
 * This class defines the FailingAppendable which implements the Appendable interface.
 */
public class FailingAppendable implements Appendable {

  @Override public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Failed");
  }

  @Override public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Failed");
  }

  @Override public Appendable append(char c) throws IOException {
    throw new IOException("Failed");
  }
}
