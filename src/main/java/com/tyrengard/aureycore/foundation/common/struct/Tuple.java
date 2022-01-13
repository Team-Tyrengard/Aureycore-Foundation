package com.tyrengard.aureycore.foundation.common.struct;

public class Tuple {
	public static final class Unit<A> {
		public final A a;
		public Unit(A a) { this.a = a; }
	}
	public static final class Pair<A, B> {
		public final A a;
		public final B b;
		public Pair(A a, B b) { 
			this.a = a;
			this.b = b;
		}
	}
	public static final class Triplet<A, B, C> {
		public final A a;
		public final B b;
		public final C c;
		public Triplet(A a, B b, C c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
}
