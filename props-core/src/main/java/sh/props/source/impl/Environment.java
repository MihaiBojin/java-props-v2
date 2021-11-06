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

package sh.props.source.impl;

import java.util.Map;
import sh.props.source.Source;

/** Retrieves all environment variables. */
public class Environment extends Source {
  public static final String ID = "env";

  /**
   * An unique identifier representing this source in the {@link sh.props.Registry}.
   *
   * @return an unique id
   */
  @Override
  public String id() {
    return ID;
  }

  /**
   * Initializes an {@link Environment} object from the specified id.
   *
   * @param id the identifier representing this source
   * @return a constructed Source object
   */
  @Override
  public Source from(String id) {
    if (!ID.equals(id)) {
      throw new IllegalArgumentException("Invalid id '" + id + "' for the current class " + this);
    }

    return new Environment();
  }

  /**
   * Retrieves all environment variables.
   *
   * @return a map containing all env. variables
   */
  @Override
  public Map<String, String> get() {
    return System.getenv();
  }
}
