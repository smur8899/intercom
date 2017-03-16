package com.intercom.flatten

import scala.annotation.tailrec

/**
  * Write a function that will flatten an array of arbitrarily nested arrays of integers into a flat array of integers.
  *
  * e.g. [[1,2,[3]],4] → [1,2,3,4]. If the language you're using has a function to flatten arrays,
  * you should pretend it doesn't exist.
  */
object ArrayFlattener {

  /**
    * For each element in the array,
    * If it is Nil (after the Leaf Node) return the accumulated flattened value
    * If it is an Leaf Node (Int), add the head to the accumulator and call recursively for the tail
    * Else, first element is also a list, concat head to the tail and call recursively on this list
    *
    * @param sequence Seq to flatten
    * @return sequence param flattened in the form [[1,2,[3]],4] → [1,2,3,4]
    */
  def flatten(sequence: Seq[Any]): Seq[Int] = {
    @tailrec
    def flattenInner(remainingToFlatten: Seq[Any], accumulator: Seq[Int]): Seq[Int] = {
      remainingToFlatten match {
        case Nil => accumulator
        case (head: Int) :: tail => flattenInner(tail, Seq(head) ++ accumulator)
        case (head: Seq[_]) :: tail => flattenInner(head ++ tail, accumulator)
      }
    }

    flattenInner(sequence, Seq.empty[Int]).reverse
  }

}
