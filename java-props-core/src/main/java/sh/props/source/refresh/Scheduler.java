/*
 * MIT License
 *
 * Copyright (c) 2021 Mihai Bojin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package sh.props.source.refresh;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.concurrent.ScheduledExecutorService;
import sh.props.source.refresh.util.BackgroundExecutorFactory;

public class Scheduler {

  protected final ScheduledExecutorService executor;

  /**
   * Class constructor.
   *
   * <p>Creates a new scheduled executor using {@link BackgroundExecutorFactory#create(int)}, with a
   * single thread.
   */
  public Scheduler() {
    this(BackgroundExecutorFactory.create(1));
  }

  /**
   * Class constructor.
   *
   * @param executor The executor used to schedule any refresh operations.
   */
  public Scheduler(ScheduledExecutorService executor) {
    this.executor = executor;
  }

  /**
   * Schedules a {@link RefreshableSource} for periodic data refreshes.
   *
   * <p>The initial delay is retrieved from {@link RefreshableSource#initialDelay()}. The refresh
   * period is retrieved from {@link RefreshableSource#refreshPeriod()}.
   *
   * @param source the source to reload
   */
  @SuppressWarnings("FutureReturnValueIgnored")
  public void schedule(RefreshableSource source) {
    this.executor.scheduleAtFixedRate(
        new Trigger(source),
        source.initialDelay().toNanos(),
        source.refreshPeriod().toNanos(),
        NANOSECONDS);
  }

  /**
   * Returns the default scheduler, ensuring it is initialized on first-use.
   *
   * @return the default {@link Scheduler}
   */
  public static Scheduler instance() {
    return Holder.DEFAULT;
  }

  /** Static holder for the default instance, ensuring lazy initialization. */
  private static class Holder {

    private static final Scheduler DEFAULT = new Scheduler();
  }
}
