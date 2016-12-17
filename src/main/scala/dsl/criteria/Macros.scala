/*
 * Copyright 2013 Steve Vickers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created on: Dec 16, 2016
 */
package reactivemongo.extensions.dsl.criteria

import scala.language.dynamics

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.reflect.runtime.universe._


/**
 * The '''TypedMacros''' `object` defines the
 * [[http://docs.scala-lang.org/overviews/macros/overview.html Scala Macros]]
 * used in supporting type-checked
 * [[reactivemongo.extensions.dsl.criteria.Term]] creation.
 *
 * @author svickers
 *
 */
object TypedMacros
{
	/// Class Imports
	import Typed.PropertyAccess


	def createTerm[T <: AnyRef : c.WeakTypeTag, U : c.WeakTypeTag]
		(c : Context { type PrefixType = PropertyAccess[T] })
		(statement : c.Tree)
		: c.Tree =
	{
		import c.universe._

		val q"""(..$args) => $select""" = statement;

		val selectors = select.collect {
			case Select (_, TermName (property)) => property;
			}.reverse.mkString (".");

		val propertyType = weakTypeOf[U];

		q"""new Term[${propertyType}] (${selectors})"""
	}
}

