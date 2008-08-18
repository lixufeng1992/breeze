// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Fri Aug 15 16:23:56 PDT 2008
package scalanlp.counters.shorts;

import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Short with type Float.
 * This trait is a wraooer around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
@serializable 
trait Short2FloatCounter extends FloatCounter[Short] {


  abstract override def update(k : Short, v : Float) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Short, v : Float) :Option[Float] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Short) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Short, v : Float) = {
     update(t,(this(t) + v).asInstanceOf[Float]);
   }

  /**
   * Increments the count associated with Short by Float.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Short,Float)) = incrementCount(kv._1,kv._2);

  override def default(k : Short) : Float = 0;

  override def apply(k : Short) : Float = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Short2FloatCounter  = super.clone().asInstanceOf[Short2FloatCounter]

  /**
   * Return the Short with the largest count
   */
  override  def argmax() : Short = (elements reduceLeft ((p1:(Short,Float),p2:(Short,Float)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Short with the smallest count
   */
  override  def argmin() : Short = (elements reduceLeft ((p1:(Short,Float),p2:(Short,Float)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Float = values reduceLeft ((p1:Float,p2:Float) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Float = values reduceLeft ((p1:Float,p2:Float) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Short, b :Short) = apply(a) compare apply(b);

  /**
   * Return a new Short2DoubleCounter with each Float divided by the total;
   */
  override  def normalized() : Short2DoubleCounter = {
    val normalized = new HashMap[Short,Double]() with Short2DoubleCounter;
    val total : Double = this.total
    if(total != 0.0)
      for (pair <- elements) {
        normalized.put(pair._1,pair._2 / total)
      }
    normalized
  }

  /**
   * Return the sum of the squares of the values
   */
  override  def l2norm() : Double = {
    var norm = 0.0
    for (val v <- values) {
      norm += (v * v)
    }
    return Math.sqrt(norm)
  }

  /**
   * Return a List the top k elements, along with their counts
   */
  override  def topK(k : Int) = Counters.topK[(Short,Float)](k,(x,y) => (x._2-y._2).asInstanceOf[Int])(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Short2FloatCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Short2FloatCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Float]);
    }
  }

  def -=(that : Short2FloatCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) - v).asInstanceOf[Float]);
    }
  }

  override  def *=(scale : Float) {
    transform { (k,v) => (v * scale).asInstanceOf[Float]}
  }

  override  def /=(scale : Float) {
    transform { (k,v) => (v / scale).asInstanceOf[Float]}
  }
}


object Short2FloatCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Short,Float] with Short2FloatCounter {
    private val under = new Short2FloatOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Short,Float]];
    override def apply(x : Short) = under.get(x);
    override def update(x : Short, v : Float) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}

