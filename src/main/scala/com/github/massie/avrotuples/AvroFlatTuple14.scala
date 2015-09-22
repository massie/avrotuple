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

    
// GENERATED SOURCE: DO NOT EDIT.

package com.github.massie.avrotuples

import java.io._
import java.util

import com.esotericsoftware.kryo.{Kryo, KryoSerializable}
import com.esotericsoftware.kryo.io.{Input, Output}
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.avro.io.{DecoderFactory, EncoderFactory}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter, SpecificRecord}
import org.apache.avro.util.Utf8
    
object AvroFlatTuple14 {

  val SCHEMA$ = AvroTupleSchemas.flatSchemas(13)

  val reader = new SpecificDatumReader[AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _]](SCHEMA$)
  val writer = new SpecificDatumWriter[AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _]](SCHEMA$)

  def readFromInputStream(tuple: AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _], in: InputStream) = {
    AvroFlatTuple14.reader.read(tuple, DecoderFactory.get.directBinaryDecoder(in, null))
  }

  def writeToOutputStream(tuple: AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _], out: OutputStream) = {
    AvroFlatTuple14.writer.write(tuple, EncoderFactory.get.directBinaryEncoder(out, null))
  }

  def fromInputStream(in: InputStream) : AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _] = {
    readFromInputStream(null.asInstanceOf[AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _]], in)
  }

  def fromBytes(bytes: Array[Byte]): AvroFlatTuple14[_, _, _, _, _, _, _, _, _, _, _, _, _, _] = {
    val in = new ByteArrayInputStream(bytes)
    val tuple = fromInputStream(in)
    in.close()
    tuple
  }

}

     
final case class AvroFlatTuple14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](
    @transient var _1: T1,
    @transient var _2: T2,
    @transient var _3: T3,
    @transient var _4: T4,
    @transient var _5: T5,
    @transient var _6: T6,
    @transient var _7: T7,
    @transient var _8: T8,
    @transient var _9: T9,
    @transient var _10: T10,
    @transient var _11: T11,
    @transient var _12: T12,
    @transient var _13: T13,
    @transient var _14: T14)
  extends Product14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14] with SpecificRecord with KryoSerializable with Externalizable {

  def this() = this(null.asInstanceOf[T1],
                    null.asInstanceOf[T2],
                    null.asInstanceOf[T3],
                    null.asInstanceOf[T4],
                    null.asInstanceOf[T5],
                    null.asInstanceOf[T6],
                    null.asInstanceOf[T7],
                    null.asInstanceOf[T8],
                    null.asInstanceOf[T9],
                    null.asInstanceOf[T10],
                    null.asInstanceOf[T11],
                    null.asInstanceOf[T12],
                    null.asInstanceOf[T13],
                    null.asInstanceOf[T14])

  def update(n1: T1, n2: T2, n3: T3, n4: T4, n5: T5, n6: T6, n7: T7, n8: T8, n9: T9, n10: T10, n11: T11, n12: T12, n13: T13, n14: T14): AvroFlatTuple14[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14] = {
    _1 = n1
    _2 = n2
    _3 = n3
    _4 = n4
    _5 = n5
    _6 = n6
    _7 = n7
    _8 = n8
    _9 = n9
    _10 = n10
    _11 = n11
    _12 = n12
    _13 = n13
    _14 = n14
    this
  }

  @throws(classOf[IndexOutOfBoundsException])
  override def get(i: Int): AnyRef = i match {
    case 0 => val values = new util.ArrayList[AnyRef](productArity)
      values.add(0, _1.asInstanceOf[AnyRef])
      values.add(1, _2.asInstanceOf[AnyRef])
      values.add(2, _3.asInstanceOf[AnyRef])
      values.add(3, _4.asInstanceOf[AnyRef])
      values.add(4, _5.asInstanceOf[AnyRef])
      values.add(5, _6.asInstanceOf[AnyRef])
      values.add(6, _7.asInstanceOf[AnyRef])
      values.add(7, _8.asInstanceOf[AnyRef])
      values.add(8, _9.asInstanceOf[AnyRef])
      values.add(9, _10.asInstanceOf[AnyRef])
      values.add(10, _11.asInstanceOf[AnyRef])
      values.add(11, _12.asInstanceOf[AnyRef])
      values.add(12, _13.asInstanceOf[AnyRef])
      values.add(13, _14.asInstanceOf[AnyRef])
      values.asInstanceOf[AnyRef]
    case _ => throw new IndexOutOfBoundsException(i.toString)
  }

  private def utf8string(obj: Any) = obj match {
    case u: Utf8 => u.toString
    case _ => obj
  }

  @throws(classOf[IndexOutOfBoundsException])
  override def put(i: Int, v: scala.Any): Unit = i match {
    case 0 =>
      val array = v match {
        case avroArray: GenericData.Array[_]=> avroArray
        case javaArray: util.ArrayList[_]=> javaArray
      }
      assert(array.size == productArity,
        s"Tried to put ${array.size} values into AvroTuple with productArity of $productArity")
      _1 = utf8string(array.get(0)).asInstanceOf[T1]
      _2 = utf8string(array.get(1)).asInstanceOf[T2]
      _3 = utf8string(array.get(2)).asInstanceOf[T3]
      _4 = utf8string(array.get(3)).asInstanceOf[T4]
      _5 = utf8string(array.get(4)).asInstanceOf[T5]
      _6 = utf8string(array.get(5)).asInstanceOf[T6]
      _7 = utf8string(array.get(6)).asInstanceOf[T7]
      _8 = utf8string(array.get(7)).asInstanceOf[T8]
      _9 = utf8string(array.get(8)).asInstanceOf[T9]
      _10 = utf8string(array.get(9)).asInstanceOf[T10]
      _11 = utf8string(array.get(10)).asInstanceOf[T11]
      _12 = utf8string(array.get(11)).asInstanceOf[T12]
      _13 = utf8string(array.get(12)).asInstanceOf[T13]
      _14 = utf8string(array.get(13)).asInstanceOf[T14]
    case _ => throw new IndexOutOfBoundsException(i.toString)
  }

  override def getSchema: Schema = AvroFlatTuple14.SCHEMA$

  override def toString: String = "(" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + "," + _6 + "," + _7 + "," + _8 + "," + _9 + "," + _10 + "," + _11 + "," + _12 + "," + _13 + "," + _14 + ")"

  def toBytes: Array[Byte] = {
    val byteStream = new ByteArrayOutputStream()
    AvroFlatTuple14.writeToOutputStream(this, byteStream)
    byteStream.flush()
    val bytes = byteStream.toByteArray
    byteStream.close()
    bytes
  }

  override def readExternal(in: ObjectInput): Unit = {
    AvroFlatTuple14.readFromInputStream(this, ExternalizableInput(in))
  }

  override def writeExternal(out: ObjectOutput): Unit = {
    AvroFlatTuple14.writeToOutputStream(this, ExternalizableOutput(out))
  }

  override def write(kryo: Kryo, output: Output): Unit = {
    AvroFlatTuple14.writeToOutputStream(this, output.getOutputStream)
  }

  override def read(kryo: Kryo, input: Input): Unit = {
    AvroFlatTuple14.readFromInputStream(this, input.getInputStream)
  }
    

}