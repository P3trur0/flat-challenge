package re.pescato.challenge

import scala.util.Try

/**
 * Performs integers arrays flattening.
 */
trait IntegersArrayFlattener {

  /**
   * Flattening integers array
   * @param in - input array
   * @return either an array of flattened integers if the input contains integers only or a throwable instance.
   */
  def flatten(in: Array[Any]): Either[Throwable, Array[Int]] = Try {
    flattenArray(in)
  }.toEither

  /**
   * Because of type erasure due to Array nesting, we lose some of the type benefits here.
   * Also, since this array may contains also other types than integers, this might throw exceptions.
   */
  private def flattenArray(in: Array[Any]): Array[Int] = in flatMap {
    case h: Int => Array(h)                                   //lifting h to Array
    case integers: Array[Int] => integers                     //identity operation since already flattened
    case x: Array[_] => flattenArray(x.toArray[Any])          //recursive step
  }

  /**
   * Preserves the behavior of already flattened array of integer values
   * @param in - array of values
   * @return the flattened version of the array boxed in an Either as a matter of API coherence
   */
  def flatten(in: Array[_ <: Int]): Either[Throwable, Array[_ <: Int]] = Right(in)

}

object IntegersArrayFlattener extends IntegersArrayFlattener
