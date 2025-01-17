/**
 * The MIT License
 *
 *  Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.jscripting;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.SimpleBindings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.rules.jscripting.JScriptingHandler.DEFAULT_JVM_LINGO;

public class JScriptingConditionTest {

    @Test
    public void testJSExpressionEvaluation() throws Exception {
        // given
        Condition isAdult = new JScriptingCondition(DEFAULT_JVM_LINGO,  "person.age > 18");
        Facts facts = new Facts();
        facts.put("person", new Person("foo", 20));

        // when
        boolean evaluationResult = isAdult.evaluate(facts);

        // then
        assertThat(evaluationResult).isTrue();
    }

    @Test
    public void whenDeclaredFactIsNotPresent_thenShouldReturnFalse() throws Exception {
        // given
        Condition isHot = new JScriptingCondition(DEFAULT_JVM_LINGO, "temperature > 30");
        Facts facts = new Facts();

        // when
        boolean evaluationResult = isHot.evaluate(facts);

        // then
        assertThat(evaluationResult).isFalse();
    }

    @Test
    public void testJSConditionWithExpressionAndParserContext() {
        // given
        Bindings context = new SimpleBindings();
        context.put("x", 42);
        context.put("y", 42);

        Condition condition = new JScriptingCondition(DEFAULT_JVM_LINGO,"x == y ", context);
        Facts facts = new Facts();
        // when
        boolean evaluationResult = condition.evaluate(facts);
        // then
        assertThat(evaluationResult).isTrue();
    }
}