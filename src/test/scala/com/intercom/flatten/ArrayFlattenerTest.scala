package com.intercom.flatten

class ArrayFlattenerTest extends org.scalatest.FunSuite {

  test("Nil list") {
    val nil = Nil
    val flattenedSeq = ArrayFlattener.flatten(nil)
    assert(nil == flattenedSeq)
  }

  test("simple case") {
    val simple = Seq(1, 2, 3, 4)
    val flattenedSeq = ArrayFlattener.flatten(simple)
    assert(simple == flattenedSeq)
  }

  test("nested as per question example [[1,2,[3]],4]") {
    val questionExample = Seq(Seq(1, 2, Seq(3)), 4)
    val expected = Seq(1, 2, 3, 4)
    val flattenedSeq = ArrayFlattener.flatten(questionExample)
    assert(expected == flattenedSeq)
  }

  test("nested as per question with Nils [[1,Nil,[Nil]],4]") {
    val input = Seq(Seq(1, Nil, Seq(Nil)), 4)
    val expected = Seq(1, 4)
    val flattenedSeq = ArrayFlattener.flatten(input)
    assert(expected == flattenedSeq)
  }

  test("nested as per question with Nils [[1,[],[]],4]") {
    val input = Seq(Seq(1, Seq(), Seq()), 4)
    val expected = Seq(1, 4)
    val flattenedSeq = ArrayFlattener.flatten(input)
    assert(expected == flattenedSeq)
  }

  test("test depth") {
    val input = Seq(Seq(1, Seq(2, Seq(3, Seq(4)))))
    val expected = Seq(1, 2, 3, 4)
    val flattenedSeq = ArrayFlattener.flatten(input)
    assert(expected == flattenedSeq)
  }

  test("test depth with Nil") {
    val input = Seq(Seq(Nil, 1, Seq(Nil, 2, Nil, Seq(3, Seq(4)))))
    val expected = Seq(1, 2, 3, 4)
    val flattenedSeq = ArrayFlattener.flatten(input)
    assert(expected == flattenedSeq)
  }
}
