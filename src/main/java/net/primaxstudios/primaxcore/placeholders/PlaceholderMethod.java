package net.primaxstudios.primaxcore.placeholders;

public interface PlaceholderMethod {

    //    public abstract String getIdentifier();
    String setPlaceholders(String line);

//    public String setPlaceholders(String line) {
//        Pattern pattern = Pattern.compile("%" + getIdentifier() + "_[^%]+%");
//        Matcher matcher = pattern.matcher(line);
//        StringBuilder result = new StringBuilder();
//        while (matcher.find()) {
//            String placeholder = matcher.group(0);
//            String modifiedPlaceholder = placeholder.replace(getIdentifier() + "_", "").replaceAll("%", "");
//            String replacement = setPlaceholdersAbstract(modifiedPlaceholder);
//            matcher.appendReplacement(result, Objects.requireNonNullElse(replacement, placeholder));
//        }
//        matcher.appendTail(result);
//        return result.toString();
//    }
}
