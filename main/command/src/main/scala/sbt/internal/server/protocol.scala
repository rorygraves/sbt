/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.typesafe.com>
 */
package sbt
package internal
package server

/*
 * These classes are the protocol for client-server interaction,
 * commands can come from the client side, while events are emitted
 * from sbt to inform the client of state changes etc.
 */
private[sbt] sealed trait Event

private[sbt] final case class LogEvent(level: String, message: String) extends Event

sealed trait Status
private[sbt] final case object Ready extends Status
private[sbt] final case class Processing(command: String, commandQueue: Seq[String]) extends Status

private[sbt] final case class StatusEvent(status: Status) extends Event
private[sbt] final case class ExecutionEvent(command: String, success: Boolean) extends Event

private[sbt] sealed trait Command

/**
 * An execution request - command line is the same as typed in at the command prompt
 * e.g. { "type": "exec", "command_line": "compile", "id": "29bc9b"  }
 * @param cmd
 * @param id Client generated unique id, related response events will carry this id.
 */
private[sbt] final case class Execution(cmd: String, id: String) extends Command
