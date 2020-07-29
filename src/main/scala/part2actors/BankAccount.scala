package part2actors

import akka.actor.Actor
import part2actors.BankAccount.BankAccountActor.{
  Deposit,
  Statement,
  TransactionFailure,
  TransactionSuccess,
  Withdraw
}

object BankAccount {

  object BankAccountActor {
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)
    case object Statement

    case class TransactionSuccess(message: String)
    case class TransactionFailure(message: String)
  }

  class BankAccountActor extends Actor {
    var fund = 0

    override def receive: Receive = {
      case Deposit(amount) =>
        if (amount < 0) sender() ! TransactionFailure("Invalid deposit amount")
        else {
          fund += amount
        }
      case Withdraw(amount) =>
        if (amount < 0) sender() ! TransactionFailure("Invalid withdraw amount")
        else if (amount > fund)
          sender() ! TransactionFailure("Insufficent funds")
        else {
          fund -= amount
          sender() ! TransactionSuccess(
            s"Successfully withdrawn amount : $amount"
          )
        }
      case Statement => sender() ! s"Your balance is $fund"
    }
  }
}
