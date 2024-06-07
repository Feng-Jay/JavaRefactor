public class FAKECLASS{
  void add(Node n, Context context) {
    if (!cc.continueProcessing()) {
      return;
    }

    int type = n.getType();
    String opstr = NodeUtil.opToStr(type);
    int childCount = n.getChildCount();
    Node first = n.getFirstChild();
    Node last = n.getLastChild();

    // Handle all binary operators
    if (opstr != null && first != last) {
      Preconditions.checkState(
          childCount == 2,
          "Bad binary operator \"%s\": expected 2 arguments but got %s",
          opstr, childCount);
      int p = NodeUtil.precedence(type);

      // For right-hand-side of operations, only pass context if it's
      // the IN_FOR_INIT_CLAUSE one.
      Context rhsContext = getContextForNoInOperator(context);

      // Handle associativity.
      // e.g. if the parse tree is a * (b * c),
      // we can simply generate a * b * c.
      if (last.getType() == type &&
          NodeUtil.isAssociative(type)) {
        addExpr(first, p, context);
        cc.addOp(opstr, true);
        addExpr(last, p, rhsContext);
      } else if (NodeUtil.isAssignmentOp(n) && NodeUtil.isAssignmentOp(last)) {
        // Assignments are the only right-associative binary operators
        addExpr(first, p, context);
        cc.addOp(opstr, true);
        addExpr(last, p, rhsContext);
      } else {
        unrollBinaryOperator(n, type, opstr, context, rhsContext, p, p + 1);
      }
      return;
    }

    cc.startSourceMapping(n);

    if (type == Token.TRY) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
	} else if (type == Token.CATCH) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
	} else if (type == Token.THROW) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
	} else if (type == Token.RETURN) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
	} else if (type == Token.VAR) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
	} else if (type == Token.LABEL_NAME) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
	} else if (type == Token.NAME) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
	} else if (type == Token.ARRAYLIT) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
	} else if (type == Token.PARAM_LIST) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
	} else if (type == Token.COMMA) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
	} else if (type == Token.NUMBER) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
	} else if (type == Token.TYPEOF) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
	} else if (type == Token.VOID) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
	} else if (type == Token.NOT) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
	} else if (type == Token.BITNOT) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
	} else if (type == Token.POS) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
	} else if (type == Token.NEG) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
	} else if (type == Token.HOOK) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
	} else if (type == Token.REGEXP) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
	} else if (type == Token.FUNCTION) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
	} else if (type == Token.GETTER_DEF) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
	} else if (type == Token.SETTER_DEF) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
	} else if (type == Token.SCRIPT) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
	} else if (type == Token.BLOCK) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
	} else if (type == Token.FOR) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
	} else if (type == Token.DO) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
	} else if (type == Token.WHILE) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
	} else if (type == Token.EMPTY) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
	} else if (type == Token.GETPROP) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
	} else if (type == Token.GETELEM) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
	} else if (type == Token.WITH) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
	} else if (type == Token.INC) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
	} else if (type == Token.DEC) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
	} else if (type == Token.CALL) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
	} else if (type == Token.IF) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
	} else if (type == Token.NULL) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
	} else if (type == Token.THIS) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
	} else if (type == Token.FALSE) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
	} else if (type == Token.TRUE) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
	} else if (type == Token.CONTINUE) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
	} else if (type == Token.DEBUGGER) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
	} else if (type == Token.BREAK) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
	} else if (type == Token.EXPR_RESULT) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
	} else if (type == Token.NEW) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
	} else if (type == Token.STRING_KEY) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
	} else if (type == Token.STRING) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
	} else if (type == Token.DELPROP) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
	} else if (type == Token.OBJECTLIT) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
		boolean needsParens = (context == Context.START_OF_EXPR);
		if (needsParens) {
			add("(");
		}
		add("{");
		for (Node c = first; c != null; c = c.getNext()) {
			if (c != first) {
				cc.listSeparator();
			}
			if (c.isGetterDef() || c.isSetterDef()) {
				add(c);
			} else {
				Preconditions.checkState(c.isStringKey());
				String key = c.getString();
				if (!c.isQuotedString() && !(languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(key))
						&& TokenStream.isJSIdentifier(key) && NodeUtil.isLatin(key)) {
					add(key);
				} else {
					double d = getSimpleNumber(key);
					if (!Double.isNaN(d)) {
						cc.addNumber(d);
					} else {
						addExpr(c, 1, Context.OTHER);
					}
				}
				add(":");
				addExpr(c.getFirstChild(), 1, Context.OTHER);
			}
		}
		add("}");
		if (needsParens) {
			add(")");
		}
	} else if (type == Token.SWITCH) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
		boolean needsParens = (context == Context.START_OF_EXPR);
		if (needsParens) {
			add("(");
		}
		add("{");
		for (Node c = first; c != null; c = c.getNext()) {
			if (c != first) {
				cc.listSeparator();
			}
			if (c.isGetterDef() || c.isSetterDef()) {
				add(c);
			} else {
				Preconditions.checkState(c.isStringKey());
				String key = c.getString();
				if (!c.isQuotedString() && !(languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(key))
						&& TokenStream.isJSIdentifier(key) && NodeUtil.isLatin(key)) {
					add(key);
				} else {
					double d = getSimpleNumber(key);
					if (!Double.isNaN(d)) {
						cc.addNumber(d);
					} else {
						addExpr(c, 1, Context.OTHER);
					}
				}
				add(":");
				addExpr(c.getFirstChild(), 1, Context.OTHER);
			}
		}
		add("}");
		if (needsParens) {
			add(")");
		}
		add("switch(");
		add(first);
		add(")");
		cc.beginBlock();
		addAllSiblings(first.getNext());
		cc.endBlock(context == Context.STATEMENT);
	} else if (type == Token.CASE) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
		boolean needsParens = (context == Context.START_OF_EXPR);
		if (needsParens) {
			add("(");
		}
		add("{");
		for (Node c = first; c != null; c = c.getNext()) {
			if (c != first) {
				cc.listSeparator();
			}
			if (c.isGetterDef() || c.isSetterDef()) {
				add(c);
			} else {
				Preconditions.checkState(c.isStringKey());
				String key = c.getString();
				if (!c.isQuotedString() && !(languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(key))
						&& TokenStream.isJSIdentifier(key) && NodeUtil.isLatin(key)) {
					add(key);
				} else {
					double d = getSimpleNumber(key);
					if (!Double.isNaN(d)) {
						cc.addNumber(d);
					} else {
						addExpr(c, 1, Context.OTHER);
					}
				}
				add(":");
				addExpr(c.getFirstChild(), 1, Context.OTHER);
			}
		}
		add("}");
		if (needsParens) {
			add(")");
		}
		add("switch(");
		add(first);
		add(")");
		cc.beginBlock();
		addAllSiblings(first.getNext());
		cc.endBlock(context == Context.STATEMENT);
		Preconditions.checkState(childCount == 2);
		add("case ");
		add(first);
		addCaseBody(last);
	} else if (type == Token.DEFAULT_CASE) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
		boolean needsParens = (context == Context.START_OF_EXPR);
		if (needsParens) {
			add("(");
		}
		add("{");
		for (Node c = first; c != null; c = c.getNext()) {
			if (c != first) {
				cc.listSeparator();
			}
			if (c.isGetterDef() || c.isSetterDef()) {
				add(c);
			} else {
				Preconditions.checkState(c.isStringKey());
				String key = c.getString();
				if (!c.isQuotedString() && !(languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(key))
						&& TokenStream.isJSIdentifier(key) && NodeUtil.isLatin(key)) {
					add(key);
				} else {
					double d = getSimpleNumber(key);
					if (!Double.isNaN(d)) {
						cc.addNumber(d);
					} else {
						addExpr(c, 1, Context.OTHER);
					}
				}
				add(":");
				addExpr(c.getFirstChild(), 1, Context.OTHER);
			}
		}
		add("}");
		if (needsParens) {
			add(")");
		}
		add("switch(");
		add(first);
		add(")");
		cc.beginBlock();
		addAllSiblings(first.getNext());
		cc.endBlock(context == Context.STATEMENT);
		Preconditions.checkState(childCount == 2);
		add("case ");
		add(first);
		addCaseBody(last);
		Preconditions.checkState(childCount == 1);
		add("default");
		addCaseBody(first);
	} else if (type == Token.LABEL) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
		boolean needsParens = (context == Context.START_OF_EXPR);
		if (needsParens) {
			add("(");
		}
		add("{");
		for (Node c = first; c != null; c = c.getNext()) {
			if (c != first) {
				cc.listSeparator();
			}
			if (c.isGetterDef() || c.isSetterDef()) {
				add(c);
			} else {
				Preconditions.checkState(c.isStringKey());
				String key = c.getString();
				if (!c.isQuotedString() && !(languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(key))
						&& TokenStream.isJSIdentifier(key) && NodeUtil.isLatin(key)) {
					add(key);
				} else {
					double d = getSimpleNumber(key);
					if (!Double.isNaN(d)) {
						cc.addNumber(d);
					} else {
						addExpr(c, 1, Context.OTHER);
					}
				}
				add(":");
				addExpr(c.getFirstChild(), 1, Context.OTHER);
			}
		}
		add("}");
		if (needsParens) {
			add(")");
		}
		add("switch(");
		add(first);
		add(")");
		cc.beginBlock();
		addAllSiblings(first.getNext());
		cc.endBlock(context == Context.STATEMENT);
		Preconditions.checkState(childCount == 2);
		add("case ");
		add(first);
		addCaseBody(last);
		Preconditions.checkState(childCount == 1);
		add("default");
		addCaseBody(first);
		Preconditions.checkState(childCount == 2);
		if (!first.isLabelName()) {
			throw new Error("Unexpected token type. Should be LABEL_NAME.");
		}
		add(first);
		add(":");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), true);
	} else if (type == Token.CAST) {
		Preconditions.checkState(first.getNext().isBlock() && !first.getNext().hasMoreThanOneChild());
		Preconditions.checkState(childCount >= 2 && childCount <= 3);
		add("try");
		add(first, Context.PRESERVE_BLOCK);
		Node catchblock = first.getNext().getFirstChild();
		if (catchblock != null) {
			add(catchblock);
		}
		if (childCount == 3) {
			add("finally");
			add(last, Context.PRESERVE_BLOCK);
		}
		Preconditions.checkState(childCount == 2);
		add("catch(");
		add(first);
		add(")");
		add(last, Context.PRESERVE_BLOCK);
		Preconditions.checkState(childCount == 1);
		add("throw");
		add(first);
		cc.endStatement(true);
		add("return");
		if (childCount == 1) {
			add(first);
		} else {
			Preconditions.checkState(childCount == 0);
		}
		cc.endStatement();
		if (first != null) {
			add("var ");
			addList(first, false, getContextForNoInOperator(context));
		}
		Preconditions.checkState(!n.getString().isEmpty());
		addIdentifier(n.getString());
		if (first == null || first.isEmpty()) {
			addIdentifier(n.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			addIdentifier(n.getString());
			cc.addOp("=", true);
			if (first.isComma()) {
				addExpr(first, NodeUtil.precedence(Token.ASSIGN), Context.OTHER);
			} else {
				addExpr(first, 0, getContextForNoInOperator(context));
			}
		}
		add("[");
		addArrayList(first);
		add("]");
		add("(");
		addList(first);
		add(")");
		Preconditions.checkState(childCount == 2);
		unrollBinaryOperator(n, Token.COMMA, ",", context, getContextForNoInOperator(context), 0, 0);
		Preconditions.checkState(childCount == 0);
		cc.addNumber(n.getDouble());
		Preconditions.checkState(childCount == 1);
		cc.addOp(NodeUtil.opToStrNoFail(type), false);
		addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		Preconditions.checkState(childCount == 1);
		if (n.getFirstChild().isNumber()) {
			cc.addNumber(-n.getFirstChild().getDouble());
		} else {
			cc.addOp(NodeUtil.opToStrNoFail(type), false);
			addExpr(first, NodeUtil.precedence(type), Context.OTHER);
		}
		Preconditions.checkState(childCount == 3);
		int p = NodeUtil.precedence(type);
		Context rhsContext = Context.OTHER;
		addExpr(first, p + 1, context);
		cc.addOp("?", true);
		addExpr(first.getNext(), 1, rhsContext);
		cc.addOp(":", true);
		addExpr(last, 1, rhsContext);
		if (!first.isString() || !last.isString()) {
			throw new Error("Expected children to be strings");
		}
		String regexp = regexpEscape(first.getString(), outputCharsetEncoder);
		if (childCount == 2) {
			add(regexp + last.getString());
		} else {
			Preconditions.checkState(childCount == 1);
			add(regexp);
		}
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		Preconditions.checkState(childCount == 3);
		boolean funcNeedsParens = (context == Context.START_OF_EXPR);
		if (funcNeedsParens) {
			add("(");
		}
		add("function");
		add(first);
		add(first.getNext());
		add(last, Context.PRESERVE_BLOCK);
		cc.endFunction(context == Context.STATEMENT);
		if (funcNeedsParens) {
			add(")");
		}
		Preconditions.checkState(n.getParent().isObjectLit());
		Preconditions.checkState(childCount == 1);
		Preconditions.checkState(first.isFunction());
		Preconditions.checkState(first.getFirstChild().getString().isEmpty());
		if (type == Token.GETTER_DEF) {
			Preconditions.checkState(!first.getChildAtIndex(1).hasChildren());
			add("get ");
		} else {
			Preconditions.checkState(first.getChildAtIndex(1).hasOneChild());
			add("set ");
		}
		String name = n.getString();
		Node fn = first;
		Node parameters = fn.getChildAtIndex(1);
		Node body = fn.getLastChild();
		if (!n.isQuotedString() && TokenStream.isJSIdentifier(name) && NodeUtil.isLatin(name)) {
			add(name);
		} else {
			double d = getSimpleNumber(name);
			if (!Double.isNaN(d)) {
				cc.addNumber(d);
			} else {
				addJsString(n);
			}
		}
		add(parameters);
		add(body, Context.PRESERVE_BLOCK);
		if (n.getClass() != Node.class) {
			throw new Error("Unexpected Node subclass.");
		}
		boolean preserveBlock = context == Context.PRESERVE_BLOCK;
		if (preserveBlock) {
			cc.beginBlock();
		}
		boolean preferLineBreaks = type == Token.SCRIPT
				|| (type == Token.BLOCK && !preserveBlock && n.getParent() != null && n.getParent().isScript());
		for (Node c = first; c != null; c = c.getNext()) {
			add(c, Context.STATEMENT);
			if (c.isVar()) {
				cc.endStatement();
			}
			if (c.isFunction()) {
				cc.maybeLineBreak();
			}
			if (preferLineBreaks) {
				cc.notePreferredLineBreak();
			}
		}
		if (preserveBlock) {
			cc.endBlock(cc.breakAfterBlockFor(n, context == Context.STATEMENT));
		}
		if (childCount == 4) {
			add("for(");
			if (first.isVar()) {
				add(first, Context.IN_FOR_INIT_CLAUSE);
			} else {
				addExpr(first, 0, Context.IN_FOR_INIT_CLAUSE);
			}
			add(";");
			add(first.getNext());
			add(";");
			add(first.getNext().getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			Preconditions.checkState(childCount == 3);
			add("for(");
			add(first);
			add("in");
			add(first.getNext());
			add(")");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		}
		Preconditions.checkState(childCount == 2);
		add("do");
		addNonEmptyStatement(first, Context.OTHER, false);
		add("while(");
		add(last);
		add(")");
		cc.endStatement();
		Preconditions.checkState(childCount == 2);
		add("while(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 0);
		Preconditions.checkState(childCount == 2, "Bad GETPROP: expected 2 children, but got %s", childCount);
		Preconditions.checkState(last.isString(), "Bad GETPROP: RHS should be STRING");
		boolean needsParens = (first.isNumber());
		if (needsParens) {
			add("(");
		}
		addExpr(first, NodeUtil.precedence(type), context);
		if (needsParens) {
			add(")");
		}
		if (this.languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(last.getString())) {
			add("[");
			add(last);
			add("]");
		} else {
			add(".");
			addIdentifier(last.getString());
		}
		Preconditions.checkState(childCount == 2, "Bad GETELEM: expected 2 children but got %s", childCount);
		addExpr(first, NodeUtil.precedence(type), context);
		add("[");
		add(first.getNext());
		add("]");
		Preconditions.checkState(childCount == 2);
		add("with(");
		add(first);
		add(")");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		Preconditions.checkState(childCount == 1);
		String o = type == Token.INC ? "++" : "--";
		int postProp = n.getIntProp(Node.INCRDECR_PROP);
		if (postProp != 0) {
			addExpr(first, NodeUtil.precedence(type), context);
			cc.addOp(o, false);
		} else {
			cc.addOp(o, false);
			add(first);
		}
		if (isIndirectEval(first) || n.getBooleanProp(Node.FREE_CALL) && NodeUtil.isGet(first)) {
			add("(0,");
			addExpr(first, NodeUtil.precedence(Token.COMMA), Context.OTHER);
			add(")");
		} else {
			addExpr(first, NodeUtil.precedence(type), context);
		}
		add("(");
		addList(first.getNext());
		add(")");
		boolean hasElse = childCount == 3;
		boolean ambiguousElseClause = context == Context.BEFORE_DANGLING_ELSE && !hasElse;
		if (ambiguousElseClause) {
			cc.beginBlock();
		}
		add("if(");
		add(first);
		add(")");
		if (hasElse) {
			addNonEmptyStatement(first.getNext(), Context.BEFORE_DANGLING_ELSE, false);
			add("else");
			addNonEmptyStatement(last, getContextForNonEmptyExpression(context), false);
		} else {
			addNonEmptyStatement(first.getNext(), Context.OTHER, false);
			Preconditions.checkState(childCount == 2);
		}
		if (ambiguousElseClause) {
			cc.endBlock();
		}
		Preconditions.checkState(childCount == 0);
		cc.addConstant("null");
		Preconditions.checkState(childCount == 0);
		add("this");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("false");
		Preconditions.checkState(childCount == 0);
		cc.addConstant("true");
		Preconditions.checkState(childCount <= 1);
		add("continue");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 0);
		add("debugger");
		cc.endStatement();
		Preconditions.checkState(childCount <= 1);
		add("break");
		if (childCount == 1) {
			if (!first.isLabelName()) {
				throw new Error("Unexpected token type. Should be LABEL_NAME.");
			}
			add(" ");
			add(first);
		}
		cc.endStatement();
		Preconditions.checkState(childCount == 1);
		add(first, Context.START_OF_EXPR);
		cc.endStatement();
		add("new ");
		int precedence = NodeUtil.precedence(type);
		if (NodeUtil.containsType(first, Token.CALL, NodeUtil.MATCH_NOT_FUNCTION)) {
			precedence = NodeUtil.precedence(first.getType()) + 1;
		}
		addExpr(first, precedence, Context.OTHER);
		Node next = first.getNext();
		if (next != null) {
			add("(");
			addList(next);
			add(")");
		}
		Preconditions.checkState(childCount == 1, "Object lit key must have 1 child");
		addJsString(n);
		Preconditions.checkState(childCount == 0, "A string may not have children");
		addJsString(n);
		Preconditions.checkState(childCount == 1);
		add("delete ");
		add(first);
		boolean needsParens = (context == Context.START_OF_EXPR);
		if (needsParens) {
			add("(");
		}
		add("{");
		for (Node c = first; c != null; c = c.getNext()) {
			if (c != first) {
				cc.listSeparator();
			}
			if (c.isGetterDef() || c.isSetterDef()) {
				add(c);
			} else {
				Preconditions.checkState(c.isStringKey());
				String key = c.getString();
				if (!c.isQuotedString() && !(languageMode == LanguageMode.ECMASCRIPT3 && TokenStream.isKeyword(key))
						&& TokenStream.isJSIdentifier(key) && NodeUtil.isLatin(key)) {
					add(key);
				} else {
					double d = getSimpleNumber(key);
					if (!Double.isNaN(d)) {
						cc.addNumber(d);
					} else {
						addExpr(c, 1, Context.OTHER);
					}
				}
				add(":");
				addExpr(c.getFirstChild(), 1, Context.OTHER);
			}
		}
		add("}");
		if (needsParens) {
			add(")");
		}
		add("switch(");
		add(first);
		add(")");
		cc.beginBlock();
		addAllSiblings(first.getNext());
		cc.endBlock(context == Context.STATEMENT);
		Preconditions.checkState(childCount == 2);
		add("case ");
		add(first);
		addCaseBody(last);
		Preconditions.checkState(childCount == 1);
		add("default");
		addCaseBody(first);
		Preconditions.checkState(childCount == 2);
		if (!first.isLabelName()) {
			throw new Error("Unexpected token type. Should be LABEL_NAME.");
		}
		add(first);
		add(":");
		addNonEmptyStatement(last, getContextForNonEmptyExpression(context), true);
		add("(");
		add(first);
		add(")");
	} else {
		throw new Error("Unknown type " + type + "\n" + n.toStringTree());
	}

    cc.endSourceMapping(n);
  }
}
