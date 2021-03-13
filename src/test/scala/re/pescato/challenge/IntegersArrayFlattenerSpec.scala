package re.pescato.challenge

import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class IntegersArrayFlattenerSpec extends AnyFlatSpec with should.Matchers with EitherValues {
  "IntegersArrayFlattener" should "flat an array of integers only" in {
    val input = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (input)
  }

  it should "flat an empty array" in {
    val input = Array()
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (Array())
  }

  it should "flat an array with a single integer" in {
    val input = Array(1)
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (Array(1))
  }

  it should "flat an array of integers with single nesting" in {
    val input = Array(1, 2, 3, Array(4, 10))
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (Array(1, 2, 3, 4, 10))
  }

  it should "flat a deeply nested Array of integers" in {
    val input = Array(1, 2, 3, Array(4, Array(5, Array(6, Array(7, Array(8, Array(9)))))), 10)
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  }

  it should "flat a deeply nested Array of integers with some empty array inside" in {
    val input = Array(1, Array(Array(Array(Array(Array())))), 3, Array(4, Array(), Array(6, Array(7, Array(8, Array(9))))), 10)
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (Array(1, 3, 4, 6, 7, 8, 9, 10))
  }

  it should "result in a Left case when array does not contain integers only (e.g.: sting inside)" in {
    val input = Array(1, 2, "aaaa", Array(4, 10))
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.isLeft shouldBe true
    flattened.left.value shouldBe a [MatchError]
  }

  it should "result in a Left case when array does not contain integers only (e.g.: char inside)" in {
    val input = Array(1, 2, 456, 'a', Array(4, 10, 'a'))
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.isLeft shouldBe true
    flattened.left.value shouldBe a [MatchError]
  }

  //this behavior happens because of Scala weak performance, see https://scala-lang.org/files/archive/spec/2.13/03-types.html#weak-conformance
  it should "result in a Right case when Array[Int] contains Chars inside" in {
    val input: Array[Int] = Array(1, 2, 456, 'a')
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.value should equal (Array(1, 2, 456, 97))
  }

  it should "result in a Left case when array does not contain integers only (e.g.: long inside)" in {
    val input = Array(Array(1), 33, 22, Array(4, 10), 222L)
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.isLeft shouldBe true
    flattened.left.value shouldBe a [MatchError]
  }

  it should "result in a Left case when array does not contain any integer" in {
    val input = Array("a", "b", "c", 2L)
    val flattened = IntegersArrayFlattener.flatten(input)
    flattened.isLeft shouldBe true
    flattened.left.value shouldBe a [MatchError]
  }
}
