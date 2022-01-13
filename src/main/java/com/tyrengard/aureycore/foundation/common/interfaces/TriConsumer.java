package com.tyrengard.aureycore.foundation.common.interfaces;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
	void accept(A a, B b, C c);
}
