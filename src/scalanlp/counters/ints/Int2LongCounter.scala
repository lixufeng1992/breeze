// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Fri Aug 15 16:23:56 PDT 2008
package scalanlp.counters.ints;

import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Int with type Long.
 * This trait is a wraooer around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
@serializable 
trait Int2LongCounter extends LongCounter[Int] {


  abstract override def update(k : Int, v : Long) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Int, v : Long) :Option[Long] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Int) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Int, v : Long) = {
     update(t,(this(t) + v).asInstanceOf[Long]);
   }

  /**
   * Increments the count associated with Int by Long.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Int,Long)) = incrementCount(kv._1,kv._2);

  override def default(k : Int) : Long = 0;

  override def apply(k : Int) : Long = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Int2LongCounter  = super.clone().asInstanceOf[Int2LongCounter]

  /**
   * Return the Int with the largest count
   */
  override  def argmax() : Int = (elements reduceLeft ((p1:(Int,Long),p2:(Int,Long)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Int with the smallest count
   */
  override  def argmin() : Int = (elements reduceLeft ((p1:(Int,Long),p2:(Int,Long)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Long = values reduceLeft ((p1:Long,p2:Long) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Long = values reduceLeft ((p1:Long,p2:Long) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Int, b :Int) = apply(a) compare apply(b);

  /**
   * Return a new Int2DoubleCounter with each Long divided by the total;
   */
  override  def normalized() : Int2DoubleCounter = {
    val normalized = new HashMap[Int,Double]() with Int2DoubleCounter;
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
  override  def topK(k : Int) = Counters.topK[(Int,Long)](k,(x,y) => (x._2-y._2).asInstanceOf[Int])(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Int2LongCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Int2LongCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Long]);
    }
  }

  def -=(that : Int2LongCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) - v).asInstanceOf[Long]);
    }
  }

  override  def *=(scale : Long) {
    transform { (k,v) => (v * scale).asInstanceOf[Long]}
  }

  override  def /=(scale : Long) {
    transform { (k,v) => (v / scale).asInstanceOf[Long]}
  }
}


object Int2LongCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Int,Long] with Int2LongCounter {
    private val under = new Int2LongOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Int,Long]];
    override def apply(x : Int) = under.get(x);
    override def update(x : Int, v : Long) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}

