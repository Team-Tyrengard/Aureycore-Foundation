package com.tyrengard.aureycore.foundation.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Trie<T> {
//	private static final int MAX_RETURNS = 100;
	
	public static class TrieNode<T> {
		ArrayList<TrieNode<T>> children;
		char ch;
		T value;
		
		TrieNode(char ch, T value) {
			this.children = new ArrayList<>();
			this.ch = ch;
			this.value = value;
		}
		
		List<String> getKeys() {
			ArrayList<String> keys = new ArrayList<>();
			
			if (value != null) keys.add(ch + "");
			for (TrieNode<T> child : children)
				for (String key : child.getKeys())
					if (ch == Character.MIN_VALUE) keys.add(key);
					else keys.add(ch + key);
			return keys;
		}
		
		List<T> getValues() {
			ArrayList<T> values = new ArrayList<>();
			
			if (value != null) values.add(value);
			for (TrieNode<T> child : children)
				for (T value : child.getValues())
					values.add(value);
			return values;
		}
	}
	
	private final TrieNode<T> root;
	
	public Trie() {
		root = new TrieNode<T>(Character.MIN_VALUE, null);
	}
	
	public void add(String key, T value) {
		TrieNode<T> currentNode = root;
		for (int c = 0; c < key.length(); c++) {
			boolean shouldCreateNewNode = true;
			char ch = key.charAt(c);
			
			for (TrieNode<T> node : currentNode.children) {
				if (node.ch == ch) {
					shouldCreateNewNode = false;
					currentNode = node;
					break;
				}
			}
			
			if (shouldCreateNewNode) {
				TrieNode<T> newNode = new TrieNode<T>(ch, c == key.length() - 1 ? value : null);
				currentNode.children.add(newNode);
				currentNode = newNode;
			}
		}
	}
	
	public T get(String key) { return this.get(key, false); }
	
	public T get(String key, boolean exact) {
		T lastValue = null;
		TrieNode<T> currentNode = root;
		char ch = Character.MIN_VALUE;
		for (int c = 0; c < key.length(); c++) {
			boolean nodeChanged = false;
			ch = key.charAt(c);
			
			for (TrieNode<T> node : currentNode.children) {
				if (node.ch == ch) {
					currentNode = node;
					nodeChanged = true;
					if (node.value != null) lastValue = node.value;
					break;
				}
			}
			
			if (!nodeChanged) break;
		}
		
		if (exact) return currentNode.ch == ch ? lastValue : null;
		else return lastValue;
	}
	
	public List<String> getKeys(String key) {
		TrieNode<T> currentNode = root;
		
		if (key == null || key.isEmpty()) return root.getKeys();
		else {
			char ch = Character.MIN_VALUE;
			boolean nodeExists = false;
			for (int c = 0; c < key.length(); c++) {
				nodeExists = false;
				ch = key.charAt(c);
				
				for (TrieNode<T> node : currentNode.children) {
					if (node.ch == ch) {
						nodeExists = true;
						currentNode = node;
						break;
					}
				}
				
				if (!nodeExists) return null;
			}
		}
		
		return currentNode.getKeys().stream().map(k -> {
			if (k.equalsIgnoreCase(key)) return k;
			else return key + k.substring(1);
		}).collect(Collectors.toList());
	}
	
	public void remove(String key) {
		TrieNode<T> currentNode = root;
		TrieNode<T> lastMultipleChildParentNode = null, firstChild = null;
		char ch = Character.MIN_VALUE;
		for (int c = 0; c < key.length(); c++) {
			ch = key.charAt(c);
			
			for (TrieNode<T> node : root.children) {
				if (node.ch == ch) {
					currentNode = node;
					if (currentNode.children.size() > 1) {
						lastMultipleChildParentNode = currentNode;
						firstChild = null;
					}
					else if (lastMultipleChildParentNode != null && lastMultipleChildParentNode.ch == key.charAt(c - 1)) firstChild = currentNode;
					break;
				}
			}
			
			
		}
		
		if (currentNode.ch == ch) {
			if (lastMultipleChildParentNode == currentNode) currentNode.value = null;
			else lastMultipleChildParentNode.children.remove(firstChild);
		}
	}

	public List<T> getValues() { return root.getValues(); }
}
