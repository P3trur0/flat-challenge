# flat-challenge

Simple project implementing a module for array flattening

---

## Notes

It defines a trait for Scala Integers arrays flattening. Since unflattened arrays of integers might contain either `Int` or `Array[Int]`, Scala compiler results in an actual `Array[Any]` type. E.g.:

```scala
val in: Array[Any] = Array(1, 2, Array(3, 4, 5))
```

So, in order to try to preserve a better type safety, I took the approach of implementing a method returning either a flattened `Array[Int]` if the original input was an actual array of arrays of integers or a `MatchError` otherwise:
   ```scala
    def flatten(in: Array[Any]): Either[Throwable, Array[Int]]
   ```
  
As you may notice from the type signatures above, the method returns an actual `Array[Int]` after the flattening only if the provided input actually contains integers only.  

---

## Tests

Under `src/test/scala` you find a set of unit tests as well.
