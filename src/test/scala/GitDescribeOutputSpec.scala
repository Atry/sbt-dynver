package sbtdynver

import org.scalacheck._, Prop._

object GitDescribeOutputSpec extends Properties("GitDescribeOutputSpec") {
  check("v1.0.0",                          "v1.0.0",   0, "",         "")
  check("v1.0.0+20140707-1030",            "v1.0.0",   0, "",         "+20140707-1030")
  check("v1.0.0+3-1234abcd",               "v1.0.0",   3, "1234abcd", "")
  check("v1.0.0+3-1234abcd+20140707-1030", "v1.0.0",   3, "1234abcd", "+20140707-1030")
  check("1234abcd",                        "1234abcd", 0, "",         "")
  check("1234abcd+20140707-1030",          "1234abcd", 0, "",         "+20140707-1030")
  check("HEAD+20140707-1030",              "HEAD",     0, "",         "+20140707-1030")

  def check(v: String, ref: String, dist: Int, sha: String, dirtySuffix: String) = {
    val out = GitDescribeOutput(GitRef(ref), GitCommitSuffix(dist, sha), GitDirtySuffix(dirtySuffix))
    property(s"parses $v") = GitDescribeOutput.parse(v) ?= out
  }
}
