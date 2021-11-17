/*
 * MIT License
 *
 * Copyright (c) 2021 - 2021 Mihai Bojin
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

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import sh.props.RegistryBuilder;
import sh.props.converter.Cast;
import sh.props.source.refresh.FileWatchSvc;
import sh.props.source.refresh.Scheduler;
import sh.props.textfixtures.AwaitAssertionTest;
import sh.props.textfixtures.TestFileUtil;
import sh.props.typed.BooleanProp;

public class RefreshedSourcesTest extends AwaitAssertionTest {

  @Test
  void propertyFileWithFileWatcher() throws IOException {
    // ARRANGE
    Path propFile = TestFileUtil.createTempFilePath("input.properties");

    // load existing test properties
    InputStream testData = this.getClass().getResourceAsStream("/source/standard-types.properties");
    assertThat("Could not find test data, cannot proceed", testData, notNullValue());

    // define the source
    var source = new PropertyFile(propFile);

    // initialize the registry and bind a prop
    var registry = new RegistryBuilder(source).build();
    @SuppressWarnings("VariableDeclarationUsageDistance")
    BooleanProp prop = registry.bind(new BooleanProp("a.boolean"));

    // ACT / ASSERT

    // register the source to be refreshed on modification events
    FileWatchSvc.instance().refreshOnChanges(source);

    assertThat(
        "Expecting the key to be null", registry.get("a.boolean", Cast.asBoolean()), nullValue());

    // copy the properties to a temp file
    Files.copy(testData, propFile);

    // and expect the prop to eventually be updated
    await().until(prop::get, equalTo(true));
  }

  @Test
  void propertyFileWithScheduler() throws IOException {
    // ARRANGE
    Path propFile = TestFileUtil.createTempFilePath("input.properties");

    // load existing test properties
    InputStream testData = this.getClass().getResourceAsStream("/source/standard-types.properties");
    assertThat("Could not find test data, cannot proceed", testData, notNullValue());

    // define the source
    var source = new PropertyFile(propFile);

    // initialize the registry and bind a prop
    var registry = new RegistryBuilder(source).build();
    @SuppressWarnings("VariableDeclarationUsageDistance")
    BooleanProp prop = registry.bind(new BooleanProp("a.boolean"));

    // ACT / ASSERT

    // register the source to be refreshed periodically
    Duration interval = Duration.ofMillis(100);
    Scheduler.instance().refreshEagerly(source, interval);

    assertThat(
        "Expecting the key to be null", registry.get("a.boolean", Cast.asBoolean()), nullValue());

    // copy the properties to a temp file
    Files.copy(testData, propFile);

    // and expect the prop to eventually be updated
    await().until(prop::get, equalTo(true));
  }
}
