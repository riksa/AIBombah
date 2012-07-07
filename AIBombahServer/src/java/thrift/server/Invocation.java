package thrift.server;

/**
 * An Invocation represents a method call that is prepared to execute, given
 * an idle worker thread. It contains the input and output protocols the
 * thread's processor should use to perform the usual Thrift invocation.
 */
class Invocation implements Runnable {
  private final AbstractNonblockingServer.FrameBuffer frameBuffer;

  public Invocation(final AbstractNonblockingServer.FrameBuffer frameBuffer) {
    this.frameBuffer = frameBuffer;
  }

  public void run() {
    frameBuffer.invoke();
  }
}