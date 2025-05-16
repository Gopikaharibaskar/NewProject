public static <T> T parseRecord(String dataLine, Class<T> recordType) {
    ByteBuffer buffer = ByteBuffer.wrap(dataLine.getBytes());

    try {
        List<Object> values = new ArrayList<>();
        int position = 0;

        for (Field field : recordType.getDeclaredFields()) {
            if (field.isAnnotationPresent(FieldLength.class)) {
                int length = field.getAnnotation(FieldLength.class).value();

                // Prevent BufferUnderflowException by checking available bytes
                if (position + length > buffer.limit()) {
                    System.err.println("Error: Record size mismatch. Skipping record → " + dataLine);
                    return null; // Skip incorrectly formatted records
                }

                values.add(extractBytes(buffer, position, position + length).trim());
                position += length;
            }
        }

        return recordType.getDeclaredConstructor(values.stream().map(Object::getClass).toArray(Class[]::new))
                         .newInstance(values.toArray());
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
