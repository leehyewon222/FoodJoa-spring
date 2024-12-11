
// 문자열을 합치는 함수
function combineStrings(strings) {
	let result = '';
	
	result = strings.map(str => {
        const length = str.length;

        const lengthStr = String(length).padStart(4, '0');
        return lengthStr + str;
    }).join('');
	
	return result;
}

// 이스케이프된 특수문자들을 되돌리는 함수
function unescapeHtml(str) {
    return str.replace(/&amp;/g, '&')
			.replace(/&lt;/g, '<')
			.replace(/&gt;/g, '>')
			.replace(/&quot;/g, '"')
			.replace(/&#39;/g, "'")
			.replace(/&#96;/g, '`')
			.replace(/&#92;/g, '\\')
			.replace(/&#47;/g, '/')
			.replace(/&#40;/g, '(')
			.replace(/&#41;/g, ')')
			.replace(/&#91;/g, '[')
			.replace(/&#93;/g, ']')
			.replace(/&#123;/g, '{')
			.replace(/&#125;/g, '}')
			.replace(/&#36;/g, '$')
			.replace(/&#42;/g, '*')
			.replace(/&#43;/g, '+')
			.replace(/&#45;/g, '-')
			.replace(/&#46;/g, '.')
			.replace(/&#94;/g, '^')
			.replace(/&#124;/g, '|');
}