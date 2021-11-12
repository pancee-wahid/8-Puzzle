//private void initChoice() {
//        List<Pair<String, String>> algorithmsClass = new ArrayList<>();
//        algorithmsClass.add(new Pair("BFS", "20000"));
//        algorithmsClass.add(new Pair("DFS", "21000"));
//        algorithmsClass.add(new Pair("A* Manhattan", "22000"));
//        algorithmsClass.add(new Pair("A* Euclidean", "23000"));
//
//        algorithms.setConverter(new StringConverter<>() {
//            @Override
//            public String toString(Pair<String, String> pair) {
//                return pair.getKey();
//            }
//
//            @Override
//            public Pair<String, String> fromString(String string) {
//                return null;
//            }
//        });
//
//        algorithms.getItems().add(EMPTY_PAIR);
//        algorithms.getItems().addAll(algorithmsClass);
//        algorithms.setValue(EMPTY_PAIR);
//
//    }
