/*
 * Copyright 2015 Matt Massie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.massie.avrotuples

import java.io.{File, PrintWriter}

object CodeGenerator {

  def getWildCards(n: Int) = {for (i <- 1 to n) yield "_"} mkString ", "
  def getTypes(n: Int) = {for(i <- 1 to n) yield s"T${i}"} mkString ", "

  val header =
    """/*
      | * Copyright 2015 Matt Massie
      | *
      | * Licensed under the Apache License, Version 2.0 (the "License");
      | * you may not use this file except in compliance with the License.
      | * You may obtain a copy of the License at
      | *
      | *    http://www.apache.org/licenses/LICENSE-2.0
      | *
      | * Unless required by applicable law or agreed to in writing, software
      | * distributed under the License is distributed on an "AS IS" BASIS,
      | * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      | * See the License for the specific language governing permissions and
      | * limitations under the License.
      | */
      |
    """.stripMargin

  def packageAndImports(namespace: String): String = {
    s"""
      |// GENERATED SOURCE: DO NOT EDIT.
      |
      |package ${namespace}
      |
      |import java.io._
      |import java.util
      |
      |import com.esotericsoftware.kryo.{Kryo, KryoSerializable}
      |import com.esotericsoftware.kryo.io.{Input, Output}
      |import org.apache.avro.Schema
      |import org.apache.avro.generic.GenericData
      |import org.apache.avro.io.{DecoderFactory, EncoderFactory}
      |import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter, SpecificRecord}
      |import org.apache.avro.util.Utf8
    """.stripMargin
  }

  def tupleObject(n: Int, recursiveSchema: Boolean): String = {
    val objectName = s"Avro${if (recursiveSchema) "" else "Flat"}Tuple${n}"
    s"""
       |object ${objectName} {
       |
       |  val SCHEMA$$ = AvroTupleSchemas.${if (recursiveSchema) "recursive" else "flat"}Schemas(${n-1})
       |
       |  val reader = new SpecificDatumReader[${objectName}[${getWildCards(n)}]](SCHEMA$$)
       |  val writer = new SpecificDatumWriter[${objectName}[${getWildCards(n)}]](SCHEMA$$)
       |
       |  def readFromInputStream(tuple: ${objectName}[${getWildCards(n)}], in: InputStream) = {
       |    ${objectName}.reader.read(tuple, DecoderFactory.get.directBinaryDecoder(in, null))
       |  }
       |
       |  def writeToOutputStream(tuple: ${objectName}[${getWildCards(n)}], out: OutputStream) = {
       |    ${objectName}.writer.write(tuple, EncoderFactory.get.directBinaryEncoder(out, null))
       |  }
       |
       |  def fromInputStream(in: InputStream) : ${objectName}[${getWildCards(n)}] = {
       |    readFromInputStream(null.asInstanceOf[${objectName}[${getWildCards(n)}]], in)
       |  }
       |
       |  def fromBytes(bytes: Array[Byte]): ${objectName}[${getWildCards(n)}] = {
       |    val in = new ByteArrayInputStream(bytes)
       |    val tuple = fromInputStream(in)
       |    in.close()
       |    tuple
       |  }
       |
       |}
       |
     """.stripMargin
  }

  def tupleCaseClass(n: Int, recursiveSchema: Boolean): String = {
    val types = for (i <- 1 to n) yield "T" + i
    val ctorArgs = for (i <- 1 to n) yield s"@transient var _${i}: T${i}"
    val ctorArgsString = ctorArgs.mkString("    ", ",\n    ", ")")
    val tupleName = s"Avro${if (recursiveSchema) "" else "Flat"}Tuple${n}"

    val body = s"""
      |final case class ${tupleName}[${getTypes(n)}](
      |${ctorArgsString}
      |  extends Product${n}[${getTypes(n)}] with SpecificRecord with KryoSerializable with Externalizable {
      |
      |${types.mkString("  def this() = this(null.asInstanceOf[", "],\n                    null.asInstanceOf[", "])")}
      |
      |  def update(${{for (i <- 1 to n) yield s"n${i}: T${i}"}.mkString(", ")}): ${tupleName}[${getTypes(n)}] = {
      |    ${{for (i <- 1 to n) yield s"_${i} = n${i}"}.mkString("\n    ")}
      |    this
      |  }
      |
      |  @throws(classOf[IndexOutOfBoundsException])
      |  override def get(i: Int): AnyRef = i match {
      |    case 0 => val values = new util.ArrayList[AnyRef](productArity)
      |${{for (i <- 1 to n) yield s"      values.add(${i-1}, _${i}.asInstanceOf[AnyRef])"}.mkString("\n")}
      |      values.asInstanceOf[AnyRef]
      |    case _ => throw new IndexOutOfBoundsException(i.toString)
      |  }
      |
      |  private def utf8string(obj: Any) = obj match {
      |    case u: Utf8 => u.toString
      |    case _ => obj
      |  }
      |
      |  @throws(classOf[IndexOutOfBoundsException])
      |  override def put(i: Int, v: scala.Any): Unit = i match {
      |    case 0 =>
      |      val array = v match {
      |        case avroArray: GenericData.Array[_]=> avroArray
      |        case javaArray: util.ArrayList[_]=> javaArray
      |      }
      |      assert(array.size == productArity,
      |        s"Tried to put $${array.size} values into AvroTuple with productArity of $$productArity")
      |${{for (i <- 1 to n) yield s"      _${i} = utf8string(array.get(${i-1})).asInstanceOf[T${i}]"}.mkString("\n")}
      |    case _ => throw new IndexOutOfBoundsException(i.toString)
      |  }
      |
      |  override def getSchema: Schema = ${tupleName}.SCHEMA$$
      |
      |  override def toString: String = ${{for (i <- 1 to n) yield s"_${i}"}.mkString("\"(\" + ", " + \",\" + ", " + \")\"")}
      |
      |  def toBytes: Array[Byte] = {
      |    val byteStream = new ByteArrayOutputStream()
      |    ${tupleName}.writeToOutputStream(this, byteStream)
      |    byteStream.flush()
      |    val bytes = byteStream.toByteArray
      |    byteStream.close()
      |    bytes
      |  }
      |
      |  override def readExternal(in: ObjectInput): Unit = {
      |    ${tupleName}.readFromInputStream(this, ExternalizableInput(in))
      |  }
      |
      |  override def writeExternal(out: ObjectOutput): Unit = {
      |    ${tupleName}.writeToOutputStream(this, ExternalizableOutput(out))
      |  }
      |
      |  override def write(kryo: Kryo, output: Output): Unit = {
      |    ${tupleName}.writeToOutputStream(this, output.getOutputStream)
      |  }
      |
      |  override def read(kryo: Kryo, input: Input): Unit = {
      |    ${tupleName}.readFromInputStream(this, input.getInputStream)
      |  }
    """.stripMargin
    val swap = n match {
      case 2 => s"  def swap: ${tupleName}[T2, T1] = ${tupleName}(_2, _1)"
      case _ => ""
    }
    body + "\n" + swap + "\n}"
  }

  def main(args: Array[String]) {
    for (i <- 1 to 22) {
      val writer = new PrintWriter(new File("AvroTuple" + i.toString + ".scala"))
      writer.append(header)
      writer.append(packageAndImports("com.github.massie.avrotuples"))
      writer.append(tupleObject(i, recursiveSchema = true))
      writer.append(tupleCaseClass(i, recursiveSchema = true))
      writer.flush()
      writer.close()
    }
    for (i <- 1 to 22) {
      val writer = new PrintWriter(new File("AvroFlatTuple" + i.toString + ".scala"))
      writer.append(header)
      writer.append(packageAndImports("com.github.massie.avrotuples"))
      writer.append(tupleObject(i, recursiveSchema = false))
      writer.append(tupleCaseClass(i, recursiveSchema = false))
      writer.flush()
      writer.close()
    }
  }
}
