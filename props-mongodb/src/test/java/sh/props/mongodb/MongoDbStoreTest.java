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

package sh.props.mongodb;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static sh.props.mongodb.MongoDbStore.connect;

import com.mongodb.reactivestreams.client.MongoCollection;
import java.util.Base64;
import java.util.Random;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sh.props.Registry;
import sh.props.RegistryBuilder;

@SuppressWarnings("NullAway")
class MongoDbStoreTest {
  private static final String PROPS = "props";
  private static final String CONN_STRING = "mongodb://127.0.0.1:27017/?connectTimeoutMS=2000";
  private static String DB_NAME;

  @BeforeAll
  static void beforeAll() {
    DB_NAME = generateRandomAlphanum();

    MongoCollection<Document> collection = connect(CONN_STRING, DB_NAME, PROPS);

    // create one object
    Document prop1 = new Document();
    prop1.put("_id", "my.prop");
    prop1.put("value", "value");
    collection.insertOne(prop1);
  }

  private static String generateRandomAlphanum() {
    byte[] data = new byte[7];
    new Random().nextBytes(data);
    String encoded = Base64.getMimeEncoder().encodeToString(data);
    return encoded.replaceAll("(?i)[^a-z0-9]+", "").toLowerCase();
  }

  @Test
  @Disabled
  void mongoDbStore() {
    // ARRANGE
    var source = new MongoDbStore(CONN_STRING, DB_NAME, PROPS);

    // ACT
    Registry registry = new RegistryBuilder(source).build();

    // ASSERT
    await().until(() -> registry.get("my.prop"), equalTo("value"));
  }
}
