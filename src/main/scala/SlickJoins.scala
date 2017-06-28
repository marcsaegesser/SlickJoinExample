package example

object Example {
  // import slick.driver.H2Driver.api._
  import slick.driver.MySQLDriver.api._

  def main(args: Array[String]): Unit = {

    val url = s"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
    val db = Database.forURL(url, driver="org.h2.Driver")

    val q1 = aTable.joinLeft(bTable).on(_.bId === _.id)
    println(s"q1 = ${q1.result.statements.mkString}")
    // q1 = select x2.`id`, x2.`bId`, x2.`cId`, x2.`dId`, x2.`eId`, x2.`a`, x3.`id`, x3.`b` from `a` x2 left outer join `b` x3 on x2.`bId` = x3.`id`
    println("")

    val q2 = aTable
      .joinLeft(bTable).on(_.bId === _.id)
      .joinLeft(cTable).on(_._1.cId === _.id)
    println(s"q2 = ${q2.result.statements.mkString}")
    // q2 = select x2.`id`, x2.`bId`, x2.`cId`, x2.`dId`, x2.`eId`, x2.`a`, x3.`id`, x3.`b`, x4.`id`, x4.`c` from `a` x2 left outer join `b` x3 on x2.`bId` = x3.`id` left outer join `d` x4 on x2.`cId` = x4.`id`
    println("")

    val q3 = aTable
      .join(bTable).on(_.bId === _.id)
      .joinLeft(cTable).on(_._1.cId === _.id)
      .joinLeft(dTable).on(_._1._1.dId === _.id)
    println(s"q3 = ${q3.result.statements.mkString}")
    // q3 = select x2.`id`, x2.`bId`, x2.`cId`, x2.`dId`, x2.`eId`, x2.`a`, x3.`id`, x3.`b`, x4.`id`, x4.`c`, x5.`id`, x5.`d` from `a` x2 inner join `b` x3 on x2.`bId` = x3.`id` left outer join `d` x4 on x2.`cId` = x4.`id` left outer join `d` x5 on x2.`dId` = x5.`id`
    println("")

    val q4 = aTable
      .joinLeft(bTable).on(_.bId === _.id)
      .joinLeft(cTable).on(_._1.cId === _.id)
      .joinLeft(dTable).on(_._1._1.dId === _.id)
    println(s"q4 = ${q4.result.statements.mkString}")
    // q4 = select x2.x3, x2.x4, x2.x5, x2.x6, x2.x7, x2.x8, x2.x9, x2.x10, x2.x11, x12.`id`, x12.`c`, x13.`id`, x13.`d` from (select x14.`bId` as x4, x14.`a` as x8, x14.`cId` as x5, x14.`dId` as x6, x14.`id` as x3, x14.`eId` as x7, (case when (x15.`id` is null) then null else 1 end) as x9, x15.`id` as x10, x15.`b` as x11 from `a` x14 left outer join `b` x15 on x14.`bId` = x15.`id`) x2 left outer join `d` x12 on x2.x5 = x12.`id` left outer join `d` x13 on x2.x6 = x13.`id`
    println("")

    db.close()
  }

  case class A(id: Int, bId: Int, cId: Int, dId: Int, eId: Int, a: String)
  case class B(id: Int, b: String)
  case class C(id: Int, c: String)
  case class D(id: Int, d: String)
  case class E(id: Int, e: String)

  class ATable(tag: Tag) extends Table[A](tag, "a") {
    def id = column[Int]("id", O.PrimaryKey)
    def a = column[String]("a")
    def bId = column[Int]("bId")
    def cId = column[Int]("cId")
    def dId = column[Int]("dId")
    def eId = column[Int]("eId")

    def * = (id, bId, cId, dId, eId, a) <> (A.tupled, A.unapply)
  }

  val aTable = TableQuery[ATable]

  class BTable(tag: Tag) extends Table[B](tag, "b") {
    def id = column[Int]("id", O.PrimaryKey)
    def b = column[String]("b")

    def * = (id, b) <> (B.tupled, B.unapply)
  }

  val bTable = TableQuery[BTable]

  class CTable(tag: Tag) extends Table[C](tag, "d") {
    def id = column[Int]("id", O.PrimaryKey)
    def c = column[String]("c")

    def * = (id, c) <> (C.tupled, C.unapply)
  }

  val cTable = TableQuery[CTable]

  class DTable(tag: Tag) extends Table[D](tag, "d") {
    def id = column[Int]("id", O.PrimaryKey)
    def d = column[String]("d")

    def * = (id, d) <> (D.tupled, D.unapply)
  }

  val dTable = TableQuery[DTable]

  class ETable(tag: Tag) extends Table[E](tag, "e") {
    def id = column[Int]("id", O.PrimaryKey)
    def e = column[String]("e")

    def * = (id, e) <> (E.tupled, E.unapply)
  }

  val eTable = TableQuery[ETable]

}
