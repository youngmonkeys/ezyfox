package com.tvd12.ezyfox.reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EzyClassTree {
	
	protected final List<Node> roots = new ArrayList<>();
	
	public EzyClassTree() {}
	
	public EzyClassTree(Class<?>... classes) {
		this(Arrays.asList(classes));
	}
	
	public EzyClassTree(Collection<Class<?>> classes) {
		for(Class<?> clazz : classes)
			add(clazz);
	}
	
	public void add(Class<?> clazz) {
		add(clazz, roots);
	}
	
	private void add(Class<?> clazz, List<Node> existedNodes) {
		if(existedNodes.isEmpty()) {
			existedNodes.add(new Node(clazz));
			return;
		}
		for(Node node : existedNodes) {
			if(node.clazz.equals(clazz))
				return;
		}
		List<Node> children = new ArrayList<>();
		for(Node node : existedNodes) {
			if(clazz.isAssignableFrom(node.clazz))
				children.add(node);
		}
		if(children.size() > 0) {
			Node newRootNode = new Node(clazz, children);
			existedNodes.removeAll(children);
			existedNodes.add(newRootNode);
			return;
		}
		for(Node node : existedNodes) {
			if(node.clazz.isAssignableFrom(clazz)) {
				add(clazz, node.children);
				return;
			}
		}
		existedNodes.add(new Node(clazz));
	}
	
	public List<Class<?>> toList() {
		List<Class<?>> list = new ArrayList<>();
		for(Node root : roots)
			root.appendToList(list);
		return list;
	}
	
	@Override
	public String toString() {
		return String.join("\n", roots.stream()
				.map(t -> t.toString())
				.collect(Collectors.toList()));
	}

	private static class Node {
		protected final Class<?> clazz;
		protected final List<Node> children = new ArrayList<>();
		
		private Node(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		private Node(Class<?> clazz, List<Node> children) {
			this.clazz = clazz;
			this.children.addAll(children);
		}
		
		private void appendToList(List<Class<?>> list) {
			for(Node child : children)
				child.appendToList(list);
			list.add(clazz);
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder()
					.append(clazz.getName());
			if(children.size() > 0)
				builder.append(" => ").append(children);
			return builder.toString();
		}
	}
	
}
